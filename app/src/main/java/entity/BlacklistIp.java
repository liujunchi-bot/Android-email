package entity;

/*
Qiao Qing
2020/04/04
*/

public class BlacklistIp {

  private String hostAccount;
  private String enemyIp;

  public BlacklistIp(String hostAccount, String enemyIp) {
    this.hostAccount=hostAccount;
    this.enemyIp=enemyIp;
  }

  public String getHostAccount() {
    return hostAccount;
  }

  public void setHostAccount(String hostAccount) {
    this.hostAccount = hostAccount;
  }


  public String getEnemyIp() {
    return enemyIp;
  }

  public void setEnemyIp(String enemyIp) {
    this.enemyIp = enemyIp;
  }

}
