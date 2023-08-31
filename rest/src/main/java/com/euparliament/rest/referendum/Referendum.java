package com.euparliament.rest.referendum;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
class Referendum {

  private @Id @GeneratedValue Long id;
  private String title;
  private String status;

  Referendum() {}

  Referendum(String title) {

    this.title = title;
  }

  public Long getId() {
    return this.id;
  }

  public String getTitle() {
    return this.title;
  }

  public String getStatus() {
    return this.status;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public void setTitle(String title) {
    this.title = title;
  }



  @Override
  public boolean equals(Object o) {

    if (this == o)
      return true;
    if (!(o instanceof Referendum))
      return false;
    Referendum referendum = (Referendum) o;
    return Objects.equals(this.id, referendum.id) && Objects.equals(this.title, referendum.title) && Objects.equals(this.status, referendum.status);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.id, this.title, this.status);
  }

  @Override
  public String toString() {
    return "Referendum{" + "id=" + this.id + ", title='" + this.title + ", status='" + this.status + '\'' + '}';
  }
}