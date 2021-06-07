package entity;

/*
Qiao Qing
2020/04/04
*/

public class Attachment {

  private long mailId;
  private long attachOrder;
  private long fileId;

  public Attachment(long mailId, long attachOrder, long fileId) {
    this.mailId=mailId;
    this.attachOrder=attachOrder;
    this.fileId=fileId;
  }

  public long getMailId() {
    return mailId;
  }

  public void setMailId(long mailId) {
    this.mailId = mailId;
  }


  public long getAttachOrder() {
    return attachOrder;
  }

  public void setAttachOrder(long attachOrder) {
    this.attachOrder = attachOrder;
  }


  public long getFileId() {
    return fileId;
  }

  public void setFileId(long fileId) {
    this.fileId = fileId;
  }

}
