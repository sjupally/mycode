package com.abhaya.vehicle.tracking.query.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.zeroturnaround.zip.ZipUtil;

import com.abhaya.vehicle.tracking.data.model.RawPacketData;
import com.abhaya.vehicle.tracking.services.RawDataPacketService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("v1/downloadRawPacket")
public class DownloadRawPacketQueryController 
{
	
	@Autowired private RawDataPacketService rawDataPacketService;
	
	@ApiOperation(value = "Download Raw Packet Data")
	@RequestMapping(method = RequestMethod.GET)
	public void downLoadPacketData(HttpServletRequest httpServletRequest,HttpServletResponse response) throws Exception
	{
		List<RawPacketData> rawPacketDatas = rawDataPacketService.getDataBySerialNumberAndDate(httpServletRequest.getParameter("serialNumber"),httpServletRequest.getParameter("date"));
		String tmpDirStr = System.getProperty("java.io.tmpdir");
		
		File tempDir = new File(tmpDirStr);
		if (!tempDir.exists())
		{
			tempDir.mkdir();
		}
		File dateDirTemp = new File(tmpDirStr+"/SN_"+httpServletRequest.getParameter("serialNumber")+"_"+httpServletRequest.getParameter("date"));
		if (!dateDirTemp.exists())
		{
			dateDirTemp.mkdir();
		}
		if (rawPacketDatas != null && rawPacketDatas.size() > 0)
		{
			for (RawPacketData packetData : rawPacketDatas)
			{
				File file = new File(dateDirTemp.getPath() + "/"+httpServletRequest.getParameter("date")+"_"+packetData.getCreatedDate().getHours()+"_"+packetData.getCreatedDate().getMinutes()+".txt");
				FileOutputStream out = new FileOutputStream(file);
				out.write(packetData.getRawData().getBytes());
				out.flush();
				out.close();
			}
		}
		ZipUtil.pack(dateDirTemp, new File(dateDirTemp.getPath()+".zip"));
		response.setContentType("application/zip");
        response.setHeader("Content-Disposition", "attachment; filename="+"SN_"+httpServletRequest.getParameter("serialNumber")+"_"+httpServletRequest.getParameter("date")+".zip");
        FileInputStream fis = new FileInputStream(new File(dateDirTemp.getPath()+".zip"));
        ServletOutputStream os       = response.getOutputStream();
		byte[] bufferData = new byte[1024];
		int read=0;
		while ((read = fis.read(bufferData))!= -1)
		{
			os.write(bufferData, 0, read);
		}
		os.flush();
		os.close();
		fis.close();
	}
}
