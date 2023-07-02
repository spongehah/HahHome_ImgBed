package com.spongehah.hahhome.controller;

import com.spongehah.hahhome.common.ResultMsg;
import com.spongehah.hahhome.common.config.LocalConfig;
import com.spongehah.hahhome.po.FileInfo;
import com.spongehah.hahhome.service.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author: wanggang.io
 * @Date: 2019/10/8 14:05
 * @todo
 */
@RestController
@RequestMapping("pic")
public class UploadController {

    @Autowired
    private LocalConfig localConfig;
    @Autowired
    private FileUploadService fileUploadService;


    @PostMapping("upload")
    public ResultMsg uploadFile(String jessionids , MultipartFile file) {
        if(jessionids == null || jessionids.isEmpty()){
            return ResultMsg.getFailedMsg("jessionids 不能为空");
        }
        if(!jessionids.equals("9be51d2f-bb1a-4c61-.........11d661570588000594"))    //自定义你的图片上传验证码，需此请求体中配该jsonid才能上传图片，
                                                                                // 图床配置详情参考https://blog.csdn.net/bdqx_007/article/details/102459184?spm=1001.2014.3001.5506
            return ResultMsg.getFailedMsg("jessionids 错误");

        long size = (long) file.getSize();
        if (size > localConfig.getMaxFileSize()) {
            return ResultMsg.getMsg("上传文件过大，请上传小于100MB大小的文件");
        }

        ResultMsg resultMsg = fileUploadService.uploadFile("shareX", file);

        if (resultMsg.getResult() != "SUCCESS") {
            return ResultMsg.getFailedMsg("保存文件失败");
        }

        FileInfo fileInfo = (FileInfo) resultMsg.getData();

        resultMsg.setData(fileInfo);
        return resultMsg;
    }
}
