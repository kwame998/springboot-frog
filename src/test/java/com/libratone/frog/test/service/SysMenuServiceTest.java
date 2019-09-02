package com.libratone.frog.test.service;

import com.libratone.frog.entity.ins.AuthMenuTreeIns;
import com.libratone.frog.service.SysMenuService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SysMenuServiceTest {


	@Autowired
	private SysMenuService sysMenuService;

	@Test
	public void testSelect() {
		List<AuthMenuTreeIns> authMenuTreeIns=sysMenuService.getMenuTreeByUserId(1);
		System.out.println(authMenuTreeIns.size());
	}

}
