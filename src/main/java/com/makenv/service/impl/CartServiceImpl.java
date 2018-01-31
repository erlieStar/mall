package com.makenv.service.impl;

import com.makenv.common.Const;
import com.makenv.common.ResponseCode;
import com.makenv.common.ServerResponse;
import com.makenv.dao.CartMapper;
import com.makenv.pojo.Cart;
import com.makenv.service.CartService;
import com.makenv.vo.CartVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    CartMapper cartMapper;

    @Override
    public ServerResponse<CartVo> add(Integer userId, Integer productId, Integer count) {
        if (productId == null || count == null) {
            return ServerResponse.errorCodeMsg(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        Cart cart = cartMapper.selectCartByUserIdProductId(userId, productId);
        //产品不再购物车
        if (cart == null) {
            Cart cartItem = new Cart();
            cart.setUserId(userId);
            cart.setProductId(productId);
            cart.setQuantity(count);
            cart.setChecked(Const.Cart.CHECKED);
            cartMapper.insert(cart);
        } else {
            count = count + cart.getQuantity();
            cart.setQuantity(count);
            cartMapper.updateByPrimaryKeySelective(cart);
        }
        return null;
    }

    @Override
    public ServerResponse<CartVo> update(Integer userId, Integer productId, Integer count) {
        return null;
    }

    @Override
    public ServerResponse<CartVo> deleteProduct(Integer userId, String productIds) {
        return null;
    }

    @Override
    public ServerResponse<CartVo> list(Integer userId) {
        return null;
    }

    @Override
    public ServerResponse<CartVo> selectOrUnSelect(Integer userId, Integer productId, Integer checked) {
        return null;
    }

    @Override
    public ServerResponse<Integer> getCartProductCount(Integer userId) {
        return null;
    }

    //获取购物车能买的最大的数量，如购物车中有100个，库存只有50个，则返回50个
    private CartVo getCartVoLimit(Integer userId) {
        return null;
    }

    //都选中返回true
    private boolean getAllCheckedStatus(Integer userId) {
        if (userId == null) {
            return false;
        }
        return cartMapper.selectCartProductCheckedStatusByUserId(userId) == 0;
    }
}
