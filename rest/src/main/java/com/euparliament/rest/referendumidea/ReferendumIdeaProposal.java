package com.euparliament.rest.referendumidea;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
class ReferendumIdeaProposal {

  private @Id @GeneratedValue Long id;
  private String title;

  ReferendumIdeaProposal() {}

  ReferendumIdeaProposal(String title) {

    this.title = title;
  }

  public Long getId() {
    return this.id;
  }

  public String getTitle() {
    return this.title;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  @Override
  public boolean equals(Object o) {

    if (this == o)
      return true;
    if (!(o instanceof ReferendumIdeaProposal))
      return false;
    ReferendumIdeaProposal referendumIdeaProposal = (ReferendumIdeaProposal) o;
    return Objects.equals(this.id, referendumIdeaProposal.id) && Objects.equals(this.title, referendumIdeaProposal.title);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.id, this.title);
  }

  @Override
  public String toString() {
    return "ReferendumIdeasProposal{" + "id=" + this.id + ", title='" + this.title + '\'' + '}';
  }
}