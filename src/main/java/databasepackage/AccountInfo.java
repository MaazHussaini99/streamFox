package databasepackage;

public class AccountInfo {

    private String Name;
    private String ProfileImage;
    private String Username;

    private String AccountCreationDate;
    private Boolean DeleteAccountRequest;
    private Boolean DisableAccountRequest;
    public AccountInfo(String Name, String ProfileImage, String Username,String AccountCreationDate, Boolean DeleteAccountRequest,Boolean DisableAccountRequest) {
        this.Name = Name;
        this.ProfileImage = ProfileImage;
        this.Username = Username;
        this.AccountCreationDate=AccountCreationDate;
        this.DeleteAccountRequest=DeleteAccountRequest;
        this.DisableAccountRequest=DisableAccountRequest;
    }
    public String getName() {
        return this.Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getProfileImage() {
        return this.ProfileImage;
    }

    public void setProfileImage(String ProfileImage) {
        this.ProfileImage = ProfileImage;
    }

    public String getUsername() {
        return this.Username;
    }

    public void setUsername(String Username) {
        this.Username = Username;
    }


    public String getAccountCreationDate() {
        return AccountCreationDate;
    }

    public void setAccountCreationDate(String accountCreationDate) {
        this.AccountCreationDate = accountCreationDate;
    }

    public Boolean getDeleteAccountRequest() {
        return DeleteAccountRequest;
    }

    public void setDeleteAccountRequest(Boolean deleteAccountRequest) {
        this.DeleteAccountRequest = deleteAccountRequest;
    }

    public Boolean getDisableAccountRequest() {
        return DisableAccountRequest;
    }

    public void setDisableAccountRequest(Boolean disableAccountRequest) {
        this.DisableAccountRequest = disableAccountRequest;
    }

    @Override
    public String toString() {
        return "AccountInfo{" +
                "Name='" + Name + '\'' +
                ", ProfileImage='" + ProfileImage + '\'' +
                ", Username='" + Username + '\'' +
                ", AccountCreationDate=" + AccountCreationDate +
                ", DeleteAccountRequest=" + DeleteAccountRequest +
                ", DisableAccountRequest=" + DisableAccountRequest +
                '}';
    }
}
