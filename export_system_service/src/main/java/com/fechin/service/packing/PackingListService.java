package com.fechin.service.packing;

import com.fechin.domain.cargo.PackingList;
import com.fechin.domain.cargo.PackingListExample;
import com.github.pagehelper.PageInfo;

public interface PackingListService {
    void packing(PackingList packingList);

    PageInfo<PackingList> findAllOnPage(Integer page, Integer size, PackingListExample packingListExample);

    void update(PackingList packingList);

    PackingList findById(String id);

    void delete(String id);

    void submit(String id);

    void cancel(String id);
}
