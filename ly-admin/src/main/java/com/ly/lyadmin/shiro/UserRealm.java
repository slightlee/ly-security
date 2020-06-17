package com.ly.lyadmin.shiro;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ly.common.utils.Constant;
import com.ly.lyadmin.modules.sys.mapper.SysUserMapper;
import com.ly.lyadmin.modules.sys.model.SysUser;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * description:
 *
 * @author : SLIGHTLEE
 * @date : 2019/4/25/25 22:10
 */
@Component
public class UserRealm extends AuthorizingRealm {

    @Autowired
    SysUserMapper sysUserMapper;

  /*  @Autowired
    SysMenuMapper sysMenuMapper;
*/
    /**
     * 授权(验证权限时调用)
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SysUser user = (SysUser) principals.getPrimaryPrincipal();
        Long userId = user.getUserId();
        List<String> permsList;
        // 系统管理员 拥有最高权限
        if (userId == Constant.SUPER_ADMIN){
           /* List<SysMenu> menuList = sysMenuMapper.selectList(null);
            permsList = new ArrayList<>(menuList.size());
            for (SysMenu menu : menuList){
                permsList.add(menu.getPerms());
            }*/
        }else {
            permsList = sysUserMapper.queryAllPerms(userId);
        }

        //用户权限列表
       /* Set<String> permsSet = new HashSet<>();
        for (String perms : permsList){
            if (StringUtils.isBlank(perms)){
                continue;
            }
            permsSet.addAll(Arrays.asList(perms.trim().split(",")));
        }*/

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
      //  info.setStringPermissions(permsSet);
        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken)
            throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken)authcToken;
        //查询用户信息
        SysUser sysUser = new SysUser();
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(SysUser::getUsername, token.getUsername());
        sysUser = sysUserMapper.selectOne(wrapper);
        //账号不存在
        if(sysUser == null) {
            throw new UnknownAccountException("账号或密码不正确");
        }
        //账号锁定
        if(sysUser.getStatus() == 0){
            throw new LockedAccountException("账号已被锁定,请联系管理员");
        }
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(sysUser, sysUser.getPassword(), ByteSource.Util.bytes(sysUser.getSalt()), getName());
        return info;
    }


    @Override
    public void setCredentialsMatcher(CredentialsMatcher credentialsMatcher) {
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName(ShiroUtils.hashAlgorithmName);
        hashedCredentialsMatcher.setHashIterations(ShiroUtils.hashIterations);
        super.setCredentialsMatcher(hashedCredentialsMatcher);
    }
}
