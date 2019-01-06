package com.whyble.farm.seed.domain.seeds.save;

import com.whyble.farm.seed.domain.Domain;

import java.util.List;

import lombok.Data;

@Data
public class AllSaveList extends Domain {

    private String save;

    private List<SaveList> save_list;
}
