package entity;


public class Parameter {

  private long smtpState;
  private long smtpPort;
  private long pop3State;
  private long pop3Port;
  private String domainName;
  private long userSize;


  public long getSmtpState() {
    return smtpState;
  }

  public void setSmtpState(long smtpState) {
    this.smtpState = smtpState;
  }


  public long getSmtpPort() {
    return smtpPort;
  }

  public void setSmtpPort(long smtpPort) {
    this.smtpPort = smtpPort;
  }


  public long getPop3State() {
    return pop3State;
  }

  public void setPop3State(long pop3State) {
    this.pop3State = pop3State;
  }


  public long getPop3Port() {
    return pop3Port;
  }

  public void setPop3Port(long pop3Port) {
    this.pop3Port = pop3Port;
  }


  public String getDomainName() {
    return domainName;
  }

  public void setDomainName(String domainName) {
    this.domainName = domainName;
  }


  public long getUserSize() {
    return userSize;
  }

  public void setUserSize(long userSize) {
    this.userSize = userSize;
  }

}
