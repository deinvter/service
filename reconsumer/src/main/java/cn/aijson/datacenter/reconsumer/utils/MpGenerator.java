package cn.aijson.datacenter.reconsumer.utils;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

/**
 * 代码生成器
 * @description
 */
public class MpGenerator {

    public static void main(String[] args) {
        new MpGenerator().generateCode();
    }
    public void generateCode() {
        String packageName = "cn.aijson.datacenter.reconsumer";
        //true user-->UserService, false user-->IUserService
        boolean serviceNameStartWithI = true;
        //需要自动生成的表
//        String[] tables = new String[]{"s_user_auth","s_channel","s_loan_order","s_note_phone","s_user_bank","s_user_basic_msg","s_user_info","s_user_phone_list"};
        String[] tables = new String[]{"sys_ugroup","sys_user","sys_user_group","sys_ugroup_role","sys_role","sys_perm","sys_role_perm"};
        generateByTables(serviceNameStartWithI, packageName, tables);

    }

    private void generateByTables(boolean serviceNameStartWithI, String packageName, String... tables) {
        GlobalConfig config = new GlobalConfig();
        String dbUrl = "jdbc:mysql://localhost:3306/test";
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setDbType(DbType.MYSQL)
                .setUrl(dbUrl)
                .setUsername("root")
                .setPassword("123456")
                .setDriverName("com.mysql.jdbc.Driver")
        ;
        StrategyConfig strategyConfig = new StrategyConfig();
        strategyConfig.setCapitalMode(true)
                .setEntityLombokModel(false)
                .setDbColumnUnderline(true)
                .setNaming(NamingStrategy.underline_to_camel)
                .setInclude(tables)
                .setTablePrefix("t_","");
        config.setActiveRecord(true)
                .setAuthor("cn.aijson.mart")
                .setOutputDir("/Users/apple/IdeaProjects/service/reconsumer/src/main/java")
                .setFileOverride(true);
        if (!serviceNameStartWithI) {
            config.setServiceName("%sService");
        }
        new AutoGenerator().setGlobalConfig(config)
                .setDataSource(dataSourceConfig)
                .setStrategy(strategyConfig)
                .setPackageInfo(
                        new PackageConfig().setParent(packageName)
                                .setController("controller")
                                .setEntity("entity").
                                setMapper("mapper")
                ).execute();
    }

}
