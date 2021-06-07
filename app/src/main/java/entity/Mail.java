package entity;

/*
Qiao Qing
2020/04/04
*/

public class Mail {

  private long mailId;
  private String senderAccount;
  private String receiverAccount;
  private java.sql.Timestamp dateTime;
  private String subject;
  private String content;
  private long mailState;
  private long size;

  public Mail(long mailId, String senderAccount, String receiverAccount, java.sql.Timestamp dateTime,
              String subject, String content, long mailState, long size) {
    this.mailId=mailId;
    this.senderAccount=senderAccount;
    this.receiverAccount=receiverAccount;
    this.dateTime=dateTime;
    this.subject=subject;
    this.content=content;
    this.mailState=mailState;
    this.size=size;
  }

  public Mail() { }

  public Mail(Mail mail){
    this.senderAccount = mail.senderAccount;
    this.receiverAccount = mail.receiverAccount;
    this.dateTime = mail.dateTime;
    this.subject = mail.subject;
    this.content = mail.content;
  }

  public long getMailId() {
    return mailId;
  }

  public void setMailId(long mailId) {
    this.mailId = mailId;
  }


  public String getSenderAccount() {
    return senderAccount;
  }

  public void setSenderAccount(String senderAccount) {
    this.senderAccount = senderAccount;
  }


  public String getReceiverAccount() {
    return receiverAccount;
  }

  public void setReceiverAccount(String receiverAccount) {
    this.receiverAccount = receiverAccount;
  }


  public java.sql.Timestamp getDateTime() {
    return dateTime;
  }

  public void setDateTime(java.sql.Timestamp dateTime) {
    this.dateTime = dateTime;
  }


  public String getSubject() {
    return subject;
  }

  public void setSubject(String subject) {
    this.subject = subject;
  }


  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }


  public long getMailState() {
    return mailState;
  }

  public void setMailState(long mailState) {
    this.mailState = mailState;
  }


  public long getSize() {
    return size;
  }

  public void setSize(long size) {
    this.size = size;
  }

}