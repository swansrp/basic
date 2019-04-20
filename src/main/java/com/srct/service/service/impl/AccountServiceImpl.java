/**
 * Copyright © 2018 SRC-TJ Service TG. All rights reserved.
 *
 * @Package: com.srct.service.service.impl
 * @author: xu1223.zhang
 * @date: 2018-08-06 14:38
 */
package com.srct.service.service.impl;

import com.srct.service.bo.LoginRequestInfoBO;
import com.srct.service.bo.LoginResponseInfoBO;
import com.srct.service.bo.RegistRequestInfoBO;
import com.srct.service.dao.mapper.AccountInfoMapper;
import com.srct.service.dao.po.AccountInfo;
import com.srct.service.dao.po.AccountInfoExample;
import com.srct.service.service.AccountService;
import com.srct.service.service.RedisService;
import com.srct.service.service.RedisTokenOperateService;
import com.srct.service.utils.log.Log;
import com.srct.service.utils.security.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @ClassName: AccountServiceImpl
 * @Description: TODO
 */
@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    RedisService redisService;

    @Autowired
    AccountInfoMapper accountInfoMapper;

    @Autowired
    RedisTokenOperateService redisTokenOperateService;

    /*
     * (non-Javadoc)
     *
     * @see com.srct.service.service.AccountService#login()
     */
    @Override
    public LoginResponseInfoBO login(LoginRequestInfoBO loginRequestInfoBO) {
        if (loginRequestInfoBO.getAccount() == null || loginRequestInfoBO.getPassword() == null) {
            return null;
        } else {
            AccountInfoExample accountInfoExample = new AccountInfoExample();
            AccountInfoExample.Criteria aCriteria = accountInfoExample.createCriteria();
            aCriteria.andAccountEqualTo(loginRequestInfoBO.getAccount());
            aCriteria.andValidEqualTo(1);
            List<AccountInfo> accountInfo = accountInfoMapper.selectByExample(accountInfoExample);
            if (accountInfo != null && !accountInfo.isEmpty()) {
                if (MD5Util.verify(loginRequestInfoBO.getPassword(), accountInfo.get(0).getPassword())) {
                    return generateLoginResponseInfo(accountInfo.get(0));
                }
            } else {
                // throw account doesnt exist error
                return null;
            }
        }
        return null;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.srct.service.service.AccountService#regist()
     */
    @Override
    public int regist(RegistRequestInfoBO registRequestInfoBO) {
        registRequestInfoBO.setValid(1);
        registRequestInfoBO.setPassword(MD5Util.generate(registRequestInfoBO.getPassword()));
        return accountInfoMapper.insertSelective(registRequestInfoBO);
    }

    private LoginResponseInfoBO generateLoginResponseInfo(AccountInfo accountInfo) {
        Integer uid = accountInfo.getId();
        LoginResponseInfoBO loginResponseInfoBO = new LoginResponseInfoBO();
        String accessToken = redisTokenOperateService.getAccessToken(uid);
        String refreshToken = redisTokenOperateService.getRefreshToken(uid);
        if (!StringUtils.isEmpty(accessToken)) {
            if (!StringUtils.isEmpty(refreshToken)) {
                Log.d("both accessToken and refreshToken can be use");
            } else {
                Log.d("refreshToken is Expired");
                refreshToken = MD5Util.MD5(
                        accountInfo.getAccount() + ":" + accountInfo.getPassword() + System.currentTimeMillis());
                redisTokenOperateService.setRefreshToken(uid, refreshToken);
            }
        } else {
            Log.d("accessToken is null or Expired");
            accessToken = MD5Util.MD5(accountInfo.getPassword() + System.currentTimeMillis());
            redisTokenOperateService.setAccessToken(uid, accessToken);
            if (!StringUtils.isEmpty(refreshToken)) {
                Log.d("can use refreshToken to getAccessToken");
            } else {
                Log.d("accessToken , refreshToken is Expired");
                refreshToken = MD5Util.MD5(
                        accountInfo.getAccount() + ":" + accountInfo.getPassword() + System.currentTimeMillis());
                redisTokenOperateService.setRefreshToken(uid, refreshToken);
            }
        }
        redisTokenOperateService.setUserRole(uid, accountInfo.getRole());
        loginResponseInfoBO.setRole(accountInfo.getRole());
        loginResponseInfoBO.setAccessToken(accessToken);
        loginResponseInfoBO.setRefreshToken(refreshToken);
        return loginResponseInfoBO;
    }
}
