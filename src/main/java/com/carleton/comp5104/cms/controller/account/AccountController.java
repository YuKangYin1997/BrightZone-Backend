package com.carleton.comp5104.cms.controller.account;

import com.carleton.comp5104.cms.controller.BaseController;
import com.carleton.comp5104.cms.entity.Account;
import com.carleton.comp5104.cms.service.AccountService;
import com.carleton.comp5104.cms.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RestController
public class AccountController extends BaseController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private PersonService personService;

    @PostMapping("/api/account/register")
    public ResponseEntity<Object> register(@RequestParam("emailOrUserId") String emailOrUserId) {
        HashMap<String, Object> result = new HashMap<>();

        Map<String, Object> map = accountService.registerAccount(emailOrUserId);
        boolean success = (boolean) map.get("success");
        result.put("success", success);
        if (!success) {
            result.put("errMsg", map.get("errMsg"));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
        }
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @PostMapping("/api/account/login")
    public ResponseEntity<Object> login(@RequestParam("emailOrUserId") String emailOrUserId,
                                        @RequestParam("password") String password) {
        HashMap<String, Object> result = new HashMap<>();
        Map<String, Object> map = accountService.login(emailOrUserId, password);
        Boolean success = (Boolean) map.get("success");
        if (success) {
            Account account = (Account) map.get("account");
            setUserId(account);
            result.put("success", true);
            result.put("userId", personService.findById(account.getUserId()).getPersonId());
            result.put("name", account.getName());
            result.put("accountType", account.getType());
            result.put("email", account.getEmail());
            result.put("lastLogin", account.getLastLogin());
            return ResponseEntity.status(HttpStatus.OK).body(result);
        } else {
            result.put("success", false);
            result.put("errMsg", map.get("errMsg"));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
        }
    }

    @GetMapping("/api/account/logout")
    public ResponseEntity<Object> logout(HttpServletRequest request) {
        HashMap<String, Object> result = new HashMap<>();

        HttpSession session = request.getSession(false);
        if(session != null && session.getAttribute("userId")!=null) {
            request.getSession().invalidate();
            result.put("success", true);
            return ResponseEntity.status(HttpStatus.OK).body(result);
        } else {
            result.put("success", false);
            result.put("errMsg", "Please login first!");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
        }
    }

    @PostMapping("/api/account/createRequest")
    public ResponseEntity<Object> createRequest(@RequestParam("message") String requestMessage,
                                             @RequestParam("type") String requestType) {
        HashMap<String, Object> result = new HashMap<>();

        Map<String, Object> map = accountService.createRequest(getUserId(), requestMessage, requestType);
        boolean success = (boolean) map.get("success");
        result.put("success", success);
        if (!success) {
            result.put("errMsg", map.get("errMsg"));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
        }
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @PostMapping("/api/account/passwordRecovery")
    public ResponseEntity<Object> passwordRecovery(@RequestParam("email") String email,
                                                @RequestParam("verificationCode") String verificationCode,
                                                @RequestParam("newPassword") String newPassword) {
        HashMap<String, Object> result = new HashMap<>();

        Map<String, Object> map = accountService.passwordRecovery(email, verificationCode, newPassword);
        boolean success = (boolean) map.get("success");
        result.put("success", success);
        if (!success) {
            result.put("errMsg", map.get("errMsg"));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
        }
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @PostMapping("/api/account/sendVerificationCode")
    public ResponseEntity<Object> sendVerificationCode(@RequestParam("email") String email) {
        HashMap<String, Object> result = new HashMap<>();

        Map<String, Object> map = accountService.sendVerificationCode(email);
        boolean success = (boolean) map.get("success");
        result.put("success", success);
        if (!success) {
            result.put("errMsg", map.get("errMsg"));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
        }
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @PostMapping("/api/account/updateEmail")
    public ResponseEntity<Object> updateEmail(@RequestParam("email") String email) {
        HashMap<String, Object> result = new HashMap<>();

        Map<String, Object> map = accountService.updateEmail(getUserId(), email);
//        Map<String, Object> map = new HashMap<>();
//        try {
//            map = accountService.updateEmail(getUserId(), email);
//        } catch (RuntimeException e) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
//        }
        boolean success = (boolean) map.get("success");
        result.put("success", success);
        if (!success) {
            result.put("errMsg", map.get("errMsg"));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
        }
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @PostMapping("/api/account/updatePassword")
    public ResponseEntity<Object> updatePassword(@RequestParam("password") String password) {
        HashMap<String, Object> result = new HashMap<>();

        Map<String, Object> map = accountService.updatePassword(getUserId(), password);
        boolean success = (boolean) map.get("success");
        result.put("success", success);
        if (!success) {
            result.put("errMsg", map.get("errMsg"));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
        }
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
