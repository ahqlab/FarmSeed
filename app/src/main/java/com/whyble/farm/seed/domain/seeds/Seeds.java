package com.whyble.farm.seed.domain.seeds;

import com.whyble.farm.seed.domain.Domain;
import com.whyble.farm.seed.domain.seeds.bonus.Bonus;
import com.whyble.farm.seed.domain.seeds.cash.Cash;
import com.whyble.farm.seed.domain.seeds.farm.Farm;
import com.whyble.farm.seed.domain.seeds.notice.Notice;
import com.whyble.farm.seed.domain.seeds.save.Save;

import java.util.List;

import lombok.Data;

@Data
public class Seeds extends Domain {

    private List<Save> save;

    private List<Farm> farm;

    private List<Cash> cash;

    private List<Bonus> bonus;

    private List<Notice> notice;

}
