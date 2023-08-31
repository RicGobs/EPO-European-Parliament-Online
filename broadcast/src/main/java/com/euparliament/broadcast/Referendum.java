package com.euparliament.broadcast;

import java.util.Objects;


class Referendum {

  private String title;
  private String status;

  Referendum() {}

  Referendum(String title, String status) {

    this.title = title;
    this.status = status;
  }

  public String getTitle() {
    return this.title;
  }

  public String getStatus() {
    return this.status;
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
    return Objects.equals(this.title, referendum.title) && Objects.equals(this.status, referendum.status);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.title, this.status);
  }

  @Override
  public String toString() {
    return "Referendum{" + " title='" + this.title + ", status='" + this.status + '\'' + '}';
  }
}