package com.jphf.cloud.util.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.jphf.cloud.shared.Room;
import com.jphf.cloud.shared.RoomMessage;

@FeignClient(name = "microservice-history")
public interface HistoryFeignClient {
	@RequestMapping(value = "/history", method = RequestMethod.GET)
	public List<RoomMessage> get(@RequestParam("roomId") String roomId, @RequestParam("before") long before);

	@RequestMapping(value = "/room", method = RequestMethod.GET)
	public Room getOrCreateRoom(@RequestParam("username") List<String> usernames);
}
