package entity;

/*
Qiao Qing
2020/04/04
*/

public class Contact {

  private long conId;
  private String hostAccount;
  private String friendAccount;

  public Contact(long conId, String hostAccount, String friendAccount) {
    this.conId=conId;
    this.hostAccount=hostAccount;
    this.friendAccount=friendAccount;
  }

  public long getConId() {
    return conId;
  }

  public void setConId(long conId) {
    this.conId = conId;
  }


  public String getHostAccount() {
    return hostAccount;
  }

  public void setHostAccount(String hostAccount) {
    this.hostAccount = hostAccount;
  }


  public String getFriendAccount() {
    return friendAccount;
  }

  public void setFriendAccount(String friendAccount) {
    this.friendAccount = friendAccount;
  }

}
