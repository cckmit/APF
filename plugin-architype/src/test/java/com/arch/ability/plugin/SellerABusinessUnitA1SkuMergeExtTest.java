package com.arch.ability.plugin;


import com.arch.ability.PluginContext;
import com.arch.ability.identity.BusinessIdentity;
import com.arch.ability.plugin.cmd.SkuMergeKeyCmd;
import com.arch.ability.plugin.impl.BusinessUnitA1SkuMergePlugin;
import com.arch.ability.plugin.impl.SellerASkuMergePlugin;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:spring-main.xml"})
public class SellerABusinessUnitA1SkuMergeExtTest {

    @Resource
    private PluginContext pluginContext;

    @Test
    public void validate1() {
        // 业务身份
        BusinessIdentity identity = BusinessIdentity.build().withBusinessUnitNo("BusinessUnit_NO_002");

        // 加载对应插件
        SkuMergeAbilityPoint plugin = pluginContext.load(SkuMergeAbilityPoint.class,identity);
        assertEquals(BusinessUnitA1SkuMergePlugin.class, plugin.getClass());

        SkuMergeKeyCmd cmd = new SkuMergeKeyCmd();
        cmd.setBusinessUnitNo("BUSINESS_UNIT_123456789");
        cmd.setGoodsNo("goodsNo");
        cmd.setGoodsLevel("goodsLevel");
        cmd.setOrderLine("orderLine");

        plugin.generateSkuMergeKeyReceiveSaleOrder(cmd);

        assertTrue(cmd.getReply().isOk());
        assertTrue(cmd.getReply().getSkuMergeKey().endsWith("_orderLine"));
    }

}
