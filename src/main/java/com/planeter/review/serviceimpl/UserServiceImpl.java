package com.planeter.review.serviceimpl;


import com.planeter.review.exception.ApiException;
import com.planeter.review.model.entity.UserEntity;
import com.planeter.review.model.param.LoginParam;
import com.planeter.review.model.param.UserParam;
import com.planeter.review.model.vo.UserVO;
import com.planeter.review.repository.UserRepository;
import com.planeter.review.security.JwtManager;
import com.planeter.review.security.UserDetail;
import com.planeter.review.service.ResourceService;
import com.planeter.review.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl implements UserService, UserDetailsService {
    @Autowired
    private JwtManager jwtManager;
    @Autowired
    private ResourceService resourceService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Resource
    private UserRepository userRepository;

    @Override
    public UserVO login(LoginParam param) {
        // 根据用户名查询出用户实体对象
        UserEntity user = userRepository.getByUsername(param.getUsername());
        // 若没有查到用户或者密码校验失败则抛出异常
        if (user == null || !passwordEncoder.matches(param.getPassword(), user.getPassword())) {
            throw new ApiException("账号密码错误");
        }

        UserVO userVO = new UserVO();
        userVO.setId(user.getId())
                .setUsername(user.getUsername())
                // 生成token
                .setToken(jwtManager.generate(user.getUsername()))
                .setResourceIds(resourceService.getIdsByUserId(user.getId()));
        return userVO;
    }

    @Override
    public void register(UserParam param) {
        if (userRepository.ex != null) {
            throw new ApiException("用户名重复");
        }
        UserEntity user = new UserEntity();
        // 密码默认为12345
        user.setUsername(param.getUsername()).setPassword(passwordEncoder.encode("12345"));
        save(user);
        if (CollectionUtils.isEmpty(param.getRoleIds())) {
            return;
        }
        // 再新增权限数据
        roleService.insertRolesByUserId(user.getId(), param.getRoleIds());
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        UserEntity user = userRepository.getByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("没有找到该用户");
        }
        // 获取用户权限集合
        Set<SimpleGrantedAuthority> authorities = resourceService.getIdsByUserId(user.getId())
                .stream()
                .map(String::valueOf)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());
        // 将用户实体与权限集合放入UserDetail种
        return new UserDetail(user, authorities);
    }
}
