package entity;

/*
Qiao Qing
2020/04/04
*/

public class BlacklistAccount {

  private String hostAccount;
  private String enemyAccount;

  public BlacklistAccount(String hostAccount,String enemyAccount) {
      this.hostAccount=hostAccount;
      this.enemyAccount=enemyAccount;
  }

  public String getHostAccount() {
    return hostAccount;
  }

  public void setHostAccount(String hostAccount) {
    this.hostAccount = hostAccount;
  }


  public String getEnemyAccount() {
    return enemyAccount;
  }

  public void setEnemyAccount(String enemyAccount) {
    this.enemyAccount = enemyAccount;
  }

}
