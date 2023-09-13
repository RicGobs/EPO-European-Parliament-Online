package com.euparliament.broadcast.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.springframework.amqp.rabbit.core.RabbitTemplate;

import com.euparliament.broadcast.BroadcastApplication;
import com.euparliament.broadcast.Receiver;
import com.euparliament.broadcast.exception.NotFoundException;
import com.euparliament.broadcast.model.ConsensusReferendum;
import com.euparliament.broadcast.model.Referendum;
import com.euparliament.broadcast.model.ReferendumMessage;
import com.euparliament.broadcast.model.ResourceMapping;

public class CheckTime extends Thread {

    private String title;
    private String dateStart;
    private String dateEnd;
    private Integer situation;
    private Receiver receiver;
    private ResourceMapping resourceMapping;
    final RabbitTemplate rabbitTemplate;

    public CheckTime(String title, String dateStart, String dateEnd, Integer situation, Receiver receiver){
        this.title = title;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.situation = situation;
        this.receiver = receiver;
		this.rabbitTemplate = receiver.getRabbitTemplate();
		this.resourceMapping = receiver.getResourceMapping();
    }

    public void run(){
        
		Calendar c = Calendar.getInstance();
        Date current = c.getTime();  

        DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date end = current;
        
        try {
            end = format.parse(dateEnd);
        } catch (ParseException e) {
            e.printStackTrace();
        } 

        long duration = end.getTime() - current.getTime();
        //long diffInSeconds = TimeUnit.MILLISECONDS.toMillis(duration);

        try {
            TimeUnit.MILLISECONDS.sleep(duration);
        } catch (InterruptedException e) {
            e.printStackTrace();
            return;
        }  

        Referendum referendum;
		try {
			referendum = HttpRequest.getReferendum(
					title, 
					dateStart, 
					this.resourceMapping
			);
		} catch (NotFoundException nFE) {
	    	System.out.println("\nCheckTime timeout, situation : " + situation + "Error: referendum not already received");
	    	return;
		}
        Integer status = referendum.getStatus();

        ConsensusReferendum consensusReferendum;
        try {
            consensusReferendum = HttpRequest.getConsensusReferendum(
                    title, 
					dateStart, 
                    this.resourceMapping);
        } catch (NotFoundException nFE) {
            System.out.println("\nCheckTime timeout, situation : " + situation + ", decision is already taken");
            return;
        }

        if (situation == 1){
        	// we are assuming that no more proposal answers can be received
        	if(status == 2 || status == 1) {
	        	System.out.println("\nCheckTime timeout, situation : " + this.printSituation() + ".");
	        	System.out.println("Compute decision:");
	            receiver.computeDecision(consensusReferendum.decide(), 2, referendum);
        	} else {
        		System.out.println("\nCheckTime timeout, situation : " + this.printSituation() + ", status: " + status + ". Already decided");
        	}
        } else if (situation == 2){
        	if(status == 3) {
	        	System.out.println("\nCheckTime timeout, situation : " + this.printSituation() + ", send local referendum result\n");
	            //calculate number of votes
	            Boolean decision = referendum.decide();
	
	    		// put the updates to the database
	            consensusReferendum.addProposalToRound(decision, 1, this.resourceMapping.getQueueName());
	    		HttpRequest.putConsensusReferendum(consensusReferendum, resourceMapping);
	            
	            referendum.setStatus(4);
			    HttpRequest.putReferendum(referendum, this.resourceMapping);
	    		
	    		// send decision message to broadcast
				System.out.println("Sending the voting answer: " + decision + " to all, for the following Referendum:");
				System.out.println("Title: " + this.title + ", Creation date: " + this.dateStart + "\n");
			    
			    ReferendumMessage decisionReferendumMessage = new ReferendumMessage(
				referendum.getId().getTitle(),
				4,
				this.resourceMapping.getQueueName(),
				decision,
				consensusReferendum.getProposals(),
				1,
				false,
				referendum.getId().getDateStartConsensusProposal()
			    );
			    rabbitTemplate.convertAndSend(
					BroadcastApplication.topicExchangeName, 
					"foo.bar.baz", 
					decisionReferendumMessage.toString());
        	} else {
        		System.out.println("\nInternal error: CheckTime timeout, situation : " + this.printSituation() + ", status: " + status);
        	}
        } else if (situation == 3){
        	if(status == 4) {
	        	System.out.println("\nCheckTime timeout, situation : " + this.printSituation() + ".");
	        	System.out.println("Compute decision:");
	            receiver.computeDecision(consensusReferendum.decide(), 4, referendum);
        	} else {
        		System.out.println("\nCheckTime timeout, situation : " + this.printSituation() + ", status: " + status + ". Already decided");
        	}
        }
    }

    public String getDateEnd() {
		return this.dateEnd;
	}

    public String getDateStart() {
		return this.dateStart;
	}

    public String getTitle() {
		return this.title;
	}

    public void setDateEnd(String dateEnd) {
		this.dateEnd = dateEnd;
	}

    public void setDateStart(String dateStart) {
		this.dateStart = dateStart;
	}

    public void setTitle(String title) {
		this.title = title;
	}
    
    private String printSituation() {
    	if(this.situation == 1) {
    		return "1 (end consensus proposals)";
    	}
    	if(this.situation == 2) {
    		return "2 (end citizen votes)";
    	}
    	if(this.situation == 3) {
    		return "3 (end consensus votes)";
    	}
    	return "";
    }
}
