package com.euparliament.rest.referendumideas;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
class ReferendumIdeasProposal {

  private @Id @GeneratedValue Long id;
  private String title;

  ReferendumIdeasProposal() {}

  ReferendumIdeasProposal(String title) {

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
    if (!(o instanceof ReferendumIdeasProposal))
      return false;
    ReferendumIdeasProposal referendumIdeasProposal = (ReferendumIdeasProposal) o;
    return Objects.equals(this.id, referendumIdeasProposal.id) && Objects.equals(this.title, referendumIdeasProposal.title);
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