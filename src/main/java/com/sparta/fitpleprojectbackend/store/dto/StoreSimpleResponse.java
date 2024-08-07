package com.sparta.fitpleprojectbackend.store.dto;

import com.sparta.fitpleprojectbackend.store.entity.Store;
import lombok.Getter;

@Getter
public class StoreSimpleResponse {

  private Long storeId;

  private String storeName;


  /**
   * Store 엔티티를 기반으로 StoreResponse 객체를 생성.
   *
   * @param store Store 엔티티
   */
  public StoreSimpleResponse(Store store) {
    this.storeId = store.getId();
    this.storeName = store.getStoreName();
  }
}
