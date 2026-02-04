package project.k_SoolMate.admin;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import project.k_SoolMate.common.Result;
import project.k_SoolMate.domain.item.sool.dto.SoolDTO;
import project.k_SoolMate.domain.item.sool.request.CreateSoolRequest;
import project.k_SoolMate.domain.item.sool.request.UpdateSoolRequest;
import project.k_SoolMate.domain.item.sool.service.SoolService;
import project.k_SoolMate.domain.order.OrderService;
import project.k_SoolMate.domain.order.dto.OrderDTO;
import project.k_SoolMate.domain.user.dto.UserDTO;
import project.k_SoolMate.domain.user.service.UserService;

import java.util.List;

//@PreAuthorize("hasRole('ADMIN')")
@Tag(name = "Admin API", description = "관리자 전용: 전통주 관리 및 회원 관리 기능 제공")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminController {

    private final SoolService soolService;
    private final UserService userService;
    private final OrderService orderService;

    // ==================
    // 전통주 관리
    // ==================
    @Operation(
            summary = "전체 전통주 조회",
            description = "등록된 전통주 상품 전체 목록을 조회합니다."
    )
    @GetMapping("/items/sool")
    public Result<List<SoolDTO>> getAllSool() {
        List<SoolDTO> all = soolService.getAll();
        return new Result<>(all);
    }

    @Operation(
            summary = "전통주 단건 조회",
            description = "전통주 상품 ID를 기준으로 상세 정보를 조회합니다."
    )
    @GetMapping("/items/sool/{id}")
    public Result<SoolDTO> getSool(@PathVariable("id") Long soolId) {
        SoolDTO soolInfo = soolService.getSoolInfo(soolId);
        return new Result<>(soolInfo);
    }

    @Operation(
            summary = "전통주 등록",
            description = "관리자가 새로운 전통주 상품을 등록합니다."
    )
    @PostMapping("/items/sool")
    public Result<SoolDTO> createSool(@Validated @RequestBody CreateSoolRequest request) {
        SoolDTO sool = soolService.createSool(request);
        return new Result<>(sool);
    }

    @Operation(
            summary = "전통주 정보 수정",
            description = "관리자가 전통주 ID와 변경 정보를 입력하여 상품 정보를 수정합니다."
    )
    @PatchMapping("/items/sool/{id}")
    public Result<SoolDTO> updateSool(@PathVariable("id") Long id, @Validated @RequestBody UpdateSoolRequest request) {
        SoolDTO updateSool = soolService.updateSool(id, request);
        return new Result<>(updateSool);
    }

    @Operation(
            summary = "전통주 삭제 (Soft Delete)",
            description = "관리자가 전통주 상품을 삭제합니다. 실제 삭제가 아닌 상태값 변경 방식입니다."
    )
    @DeleteMapping("/items/sool/{id}")
    public Result<SoolDTO> deleteSool(@PathVariable("id") Long id) {
        SoolDTO soolDTO = soolService.deleteSool(id);
        return new Result<>(soolDTO);
    }

    // ==================
    // 회원 관리
    // ==================
    @Operation(
            summary = "전체 회원 조회",
            description = "등록된 모든 회원 정보를 조회합니다. (관리자 전용)"
    )
    @GetMapping("/user/")
    public Result<List<UserDTO>> getAllUser() {
        List<UserDTO> allUser = userService.getAllUser();
        return new Result<>(allUser);
    }

    @Operation(
            summary = "회원 단건 조회",
            description = "회원 ID를 기준으로 특정 회원 정보를 조회합니다."
    )
    @GetMapping("/user/{id}")
    public Result<UserDTO> getUser(@PathVariable("id") Long id) {
        UserDTO myInfo = userService.getMyInfo(id);
        return new Result<>(myInfo);
    }

    /**
     * 주문 전체 조회(AFTER)
     */
    @Operation(
            summary = "전체 주문 조회 (ADMIN 전용), n+1 fetch 해결버젼",
            description = "모든 주문 내역을 조회합니다. 관리자만 호출 가능하도록 설계하는 것이 일반적입니다."
    )
    @GetMapping("/order")
    public Result<List<OrderDTO>> getAllOrders() {
        List<OrderDTO> allOrders = orderService.getAllOrders();
        return new Result<>(allOrders);
    }

    /**
     * 주문 전체 조회(BEFORE)
     */
    @Operation(
            summary = "전체 주문 조회 (ADMIN 전용), n+1 터지는 버젼",
            description = "모든 주문 내역을 조회합니다. 관리자만 호출 가능하도록 설계하는 것이 일반적입니다."
    )
    @GetMapping("/order/test/before")
    public Result<List<OrderDTO>> getAllOrdersWithoutFetch() {
        List<OrderDTO> allOrders = orderService.getAllOrdersWithoutFetch();
        return new Result<>(allOrders);
    }

    /**
     * 주문 전체 조회(BEFORE)
     */
    @Operation(
            summary = "전체 주문 조회 (ADMIN 전용), n+1 안터지는 버젼",
            description = "모든 주문 내역을 조회합니다. 관리자만 호출 가능하도록 설계하는 것이 일반적입니다."
    )
    @GetMapping("/order/test/after")
    public Result<List<OrderDTO>> getAllOrdersWithFetch() {
        List<OrderDTO> allOrders = orderService.getAllOrdersWithFetch();
        return new Result<>(allOrders);
    }

    /**
     * 주문 단건 조회
     */
    @Operation(
            summary = "단건 주문 조회 (ADMIN 전용)",
            description = "단건 주문 내역을 조회합니다."
    )
    @GetMapping("/order/{orderId}")
    public Result<AdminOrderDTO> getOrder(@PathVariable("orderId") Long orderId) {
        AdminOrderDTO adminOrder = orderService.getAdminOrder(orderId);
        return new Result<>(adminOrder);
    }
}
