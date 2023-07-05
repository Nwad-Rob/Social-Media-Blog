package Service;

import DAO.AccountDAO;
import Model.Account;

import java.util.List;


public class AccountService {
    public AccountDAO accountDAO;

    public AccountService(){
        accountDAO = new AccountDAO();
    }
    public AccountService(AccountDAO accountDAO){
        this.accountDAO = accountDAO;
    }
   
// TODO: Use the accountDAO to retrieve all accounts.

    public List<Account> getAllAccounts() {
        return accountDAO.getAllAccounts();
    }
   
// TODO: Use the accountDAO to persist an account to the database.

    public Account addAccount(Account account) {
      if (accountDAO.getAccountByUsername(account.getUsername()) != null){
        return null;
       }
      if (account.getPassword().length() > 4 && !(account.getUsername().isBlank())){
         accountDAO.insertAccount(account);
         return account;
       }
        return null;
    }

// TODO: Use the accountDAO to retrieve all usernames.
    public List<Account> getUser() {
        return accountDAO.getUser();
    }

    // TODO: Use the accountDAO to persist an account to the database for userLogin.

    public Account login(Account account) {
        Account a = accountDAO.getAccountByUsername(account.getUsername());

        if (a != null && a.getPassword().equals(account.password)){
            return a;
        }
        return null;
      }
}