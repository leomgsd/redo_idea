package com.service;

import com.domain.PromotionAd;
import com.domain.PromotionAdVO;
import com.github.pagehelper.PageInfo;


public interface PromotionAdService {
    public PageInfo<PromotionAd>  finaAllPromotionAdByPage(PromotionAdVO promotionAdVO);

    public void savePromotionAd(PromotionAd promotionAd);

    public void updatePromotionAd(PromotionAd promotionAd);

    public PromotionAd findPromotionAdById(int id);

    public void updatePromotionAdStatus(int id, int status);


}
