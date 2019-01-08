package com.whyble.farm.seed.domain.notice;

import com.whyble.farm.seed.domain.Domain;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class NoticeList extends Domain {

    List<Notice> notice;
}
