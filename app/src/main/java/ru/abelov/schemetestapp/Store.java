/*
 * Filename	: Store.java
 * Function	:
 * Comment 	:
 * History	: 2016/05/22, ruinnel, Create
 *
 * Version	: 1.0
 * Author   : Copyright (c) 2016 by JC Square Inc. All Rights Reserved.
 */

package ru.abelov.schemetestapp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ru.abelov.schemeTimeComponent.entity.IDaySchedule;
import ru.abelov.schemeTimeComponent.entity.IStore;
import ru.abelov.schemeTimeComponent.entity.IWeekSchedule;

public class Store implements Serializable, IStore {

    public Long id;
    public Long brandId;
    public String storeNm;
    public String tel;
    public String addr1;
    public Double latitude;
    public Double longitude;
    public String tip;
    public String intro;
    public Float distance;
    public Boolean useSelforder;
    public Boolean useReserve;
    public Boolean useParty;
    /**
     * @deprecated Используете поле WeekSchedule -> days -> orderBegin
    */
//    @Deprecated
    public String orderBegin;

    /**
     * @deprecated Используете поле WeekSchedule -> days -> orderEnd
     */
//    @Deprecated
    public String orderEnd;
    public String budget;
    public List<String> imgsUrl;
    public String imageUrl;
    public String nearestMetroStation;
    public float rating;
    public Integer ratingCount;
    public int bookmark;
    public String currency;
    public String logoImg;

    //    //iiko delivery
    public String externalId; //May come in use when new delivery types are added (except iiko)
    public String externalType;
    public Double externalMinPrice;  //we should not accept orders that cost less than externalMinPrice

    public Date openDt;
    public Boolean useReview;
    public int politics = 2;
    public Float rsvDeposit;
    public Float prtDeposit;
    public String timezone;
    public float taxRate;
    public String city;
    public String addr2;
    public String zip;
    /**
     * Расписание работы
    */
    public WeekSchedule weekSchedule;

    public boolean isLoaded = false;

    @Deprecated
    @Override
    public String getOrderBegin() {
        return orderBegin;
    }

    @Deprecated
    @Override
    public String getOrderEnd() {
        return orderEnd;
    }

    @Override
    public IWeekSchedule getSchedule() {
        return weekSchedule;
    }

    @Override
    public String getTimeFormat() {
        return "HHmm";
    }
//        public String homepage;
    //    public Double externalMinPrice;  //we should not accept orders that cost less than externalMinPrice

}

///*
// * Filename	: Store.java
// * Function	:
// * Comment 	:
// * History	: 2016/05/22, ruinnel, Create
// *
// * Version	: 1.0
// * Author   : Copyright (c) 2016 by JC Square Inc. All Rights Reserved.
// */
//
//package com.bak.app.api.bean;
//
//        import java.io.Serializable;
//        import java.util.Date;
//        import java.util.List;
//
//public class FilteredStore implements Serializable {
//    public Long id;
//    public Long serviceId;
//    public Long tenantId;
//    public Long brandId;
//    public String storeCd;
//    public String svcSt;
//    public String storeSt;
//    public String storeTp;
//    public String bizNo;
//    public String ownerNm;
//    public String storeNm;
//    public String bizCat;
//    public String email;
//    public String faxCountryCd;
//    public String fax;
//    public String mbCountryCd;
//    public String mb;
//    public String telCountryCd;
//    public String tel;
//    public String country;
//    public String region;
//    public String city;
//    public String addr1;
//    public String addr2;
//    public String zip;
//    public Double latitude;
//    public Double longitude;
//    public String timezone;
//    public String deposit;
//    public String buinessHour;
//    public String tip;
//    public String intro;
//    public Long amdinId;
//    public Boolean external;
//    public Boolean hasNvr;
//    public float taxMerchant;
//    public float taxBank;
//    public String taxTp;
//    public int countSection;
//    public Boolean orderEnabled;
//    public int capacity;
//    public String imgId;
//    public String mapX;
//    public String mapY;
//    public Float distance;
//    public Boolean useStamp;
//    public Boolean useReview;
//    public Boolean useSelforder;
//    public Boolean useReserve;
//    public Boolean useParty;
//    public String orderBegin;
//    public String orderEnd;
//    public String budget;
//    public int rsvDeposit;
//    public int prtDeposit;
//    public int stampCnt;
//    public String stampIcon;
//    public String stampBg;
//    public int stampCoupon;
//    public String stampTerm;
//
//    public String pgKind;
//    public String pgMerchantId;
//    public String pgPrivateKey;
//    public String pgPaymentKey;
//    public String pgUseTp;
//    public List<String> imgsUrl;
//
//    public String externalId; //May come in use when new delivery types are added (except iiko)
//    public String externalType;
//    public Double externalMinPrice;  //we should not accept orders that cost less than externalMinPrice
//
//
//    public String imageUrl;
//    public String nearestMetroStation;
//
//    public List<Stamp> stamps;
//    public List<Coupon> coupons;
//
//    public float rating;
//
//    public int bookmark;
//    public String currency;
////
//
//}

