package com.zhc.collaborativeinnovation.action.back;

import com.zhc.collaborativeinnovation.service.EnterpriseService;
import com.zhc.collaborativeinnovation.vo.Enterprise;
import com.zhc.collaborativeinnovation.vo.User;
import com.zhc.core.action.BaseAction;
import com.zhc.core.realms.LoginRealm;
import com.zhc.core.util.FileUtil;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;

import java.util.List;

@Namespace("/enterprise")
@ParentPackage("struts-default")
@Controller
public class EnterpriseAction extends BaseAction {

    private String fileUrl;

    private Enterprise enterprise;

    private List<Enterprise> enterpriseList;

    @Autowired
    @Qualifier("enterpriseService")
    private EnterpriseService enterpriseService;

    @Action(value = "auth", results = {@Result(name = "success", type = "redirect", location = "enterpriseinfo")})
    public String auth() {
        log.info("auth:{}",enterprise.getName());
        LoginRealm.ShiroUser shiroUser = getShiroUser();
        if (null != shiroUser) {
            String username = shiroUser.getUsername();
            Enterprise enterprise = enterpriseService.getByUsername(username);
            if (enterprise == null || Enterprise.REAUTH == enterprise.getStatus()) {
                if (null == enterprise) {
                    enterprise = new Enterprise();
                }
                enterprise.setName(this.enterprise.getName());
                enterprise.setSummary(this.enterprise.getSummary());
                enterprise.setAddress(this.enterprise.getAddress());
                byte[] bytes = FileUtil.getFile(fileUrl);
                enterprise.setLicense(bytes);
                enterprise.setStatus(Enterprise.REQUEST);
                User user = new User();
                user.setUsername(username);
                enterprise.setCorporation(user);
                enterpriseService.saveOrUpdate(enterprise);
                FileUtil.delFile(fileUrl);
            }
        }
        return SUCCESS;
    }

    @Action(value = "authentication", results = {@Result(name = "success", type = "freemarker", location = "authentication.ftl")
            , @Result(name = "enterpriseinfo", type = "chain", params = {"actionName", "enterpriseinfo", "namespace", "enterprise"})})
    public String authentication() {
        log.info("authentication");
        String username = getCurrUsername();
        enterprise = enterpriseService.getByUsername(username);
        if (enterprise == null || Enterprise.REAUTH == enterprise.getStatus()) {
            return SUCCESS;
        } else {
            return "enterpriseinfo";
        }
    }

    @Action(value = "authenticationlist", results = {@Result(name = "success", type = "freemarker", location = "authenticationlist.ftl")})
    public String authenticationlist() {
        log.info("authenticationlist");
        pages = enterpriseService.getPagesByStatus(Enterprise.REQUEST);
        enterpriseList = enterpriseService.listByStatus(curPage, Enterprise.REQUEST);
        return SUCCESS;
    }

    @Action(value = "enterpriselist", results = {@Result(name = "success", type = "freemarker", location = "enterpriselist.ftl")})
    public String enterpriselist() {
        log.info("enterpriselist");
        pages = enterpriseService.getPagesByStatus(Enterprise.SUCCESS);
        enterpriseList = enterpriseService.listByStatus(curPage, Enterprise.SUCCESS);
        return SUCCESS;
    }

    @Action(value = "enterpriseinfo", results = {@Result(name = "success", type = "freemarker", location = "enterpriseinfo.ftl")})
    public String enterpriseinfo() {
        log.info("enterpriseinfo");
        if (enterprise == null) {
            String username = getCurrUsername();
            enterprise = enterpriseService.getByUsername(username);
        } else {
            enterprise = enterpriseService.get(Enterprise.class, enterprise.getId());
        }
        return SUCCESS;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public Enterprise getEnterprise() {
        return enterprise;
    }

    public void setEnterprise(Enterprise enterprise) {
        this.enterprise = enterprise;
    }

    public List<Enterprise> getEnterpriseList() {
        return enterpriseList;
    }

    public void setEnterpriseList(List<Enterprise> enterpriseList) {
        this.enterpriseList = enterpriseList;
    }
}
