package entity;

/*
Qiao Qing
2020/04/04
*/

public class User {

  private String nickname;
  private String account;
  private String password;
  private long role;
  private long smtpstate;
  private long pop3State;
  private long usedsize;

  public User(String nickname, String account, String password, int role, int smtpstate, int pop3State, int usedsize) {
    this.nickname=nickname;
    this.account=account;
    this.password=password;
    this.role=role;
    this.smtpstate=smtpstate;
    this.pop3State=pop3State;
    this.usedsize=usedsize;
  }
  public User(){
  }

  public User(String nickname, String account, String password) {
    this.nickname=nickname;
    this.account=account;
    this.password=password;
  }

  public String getNickname() {
    return nickname;
  }

  public void setNickname(String nickname) {
    this.nickname = nickname;
  }


  public String getAccount() {
    return account;
  }

  public void setAccount(String account) {
    this.account = account;
  }


  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }


  public long getRole() {
    return role;
  }

  public void setRole(long role) {
    this.role = role;
  }


  public long getSmtpstate() {
    return smtpstate;
  }

  public void setSmtpstate(long smtpstate) {
    this.smtpstate = smtpstate;
  }


  public long getPop3State() {
    return pop3State;
  }

  public void setPop3State(long pop3State) {
    this.pop3State = pop3State;
  }


  public long getUsedsize() {
    return usedsize;
  }

  public void setUsedsize(long usedsize) {
    this.usedsize = usedsize;
  }

}