package istic.m2.project.gofback.repositories.paging;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

/**
 * this class allows to use a paging using an offset and a limit instead of a page number and a page size
 */
public class OffsetLimitPageRequest extends PageRequest {
    private int offset;

    public OffsetLimitPageRequest(int offset, int limit, Sort sort) {
        super(offset, limit, sort);
        this.offset = offset;
    }

    public static PageRequest of(int offset, int limit) {
        return new OffsetLimitPageRequest(offset, limit, Sort.unsorted());
    }

    public static PageRequest of(int offset, int limit, Sort sort) {
        return new OffsetLimitPageRequest(offset, limit, sort);
    }

    @Override
    public long getOffset() {
        return this.offset;
    }

    @Override
    public boolean equals(Object obj) {
        if (super.equals(obj)) {
            return false;
        }
        return offset == ((OffsetLimitPageRequest) obj).getOffset();
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
