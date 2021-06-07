package entity;

public class ContactHelp {


        private String conId;
        private String hostAccount;
        private String friendAccount;

        public ContactHelp(String conId, String hostAccount, String friendAccount) {
            this.conId = conId;
            this.hostAccount = hostAccount;
            this.friendAccount = friendAccount;
        }

        public String getConId() {
            return conId;
        }

        public void setConId(String conId) {
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
