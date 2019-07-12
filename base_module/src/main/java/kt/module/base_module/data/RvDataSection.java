package kt.module.base_module.data;

import com.chad.library.adapter.base.entity.SectionEntity;

public class RvDataSection extends SectionEntity<RvData> {
    public RvDataSection(boolean isHeader, String header) {
        super(isHeader, header);
    }

    public RvDataSection(RvData rvData) {
        super(rvData);
    }
}
