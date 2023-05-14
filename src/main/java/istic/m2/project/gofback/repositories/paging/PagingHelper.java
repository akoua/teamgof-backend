package istic.m2.project.gofback.repositories.paging;

import istic.m2.project.gofback.controllers.dto.ResponseDto;

import java.util.HashMap;
import java.util.Map;

public class PagingHelper {
    private PagingHelper() throws IllegalAccessException {
        throw new IllegalAccessException("Can't initiate a utility class");
    }

    /**
     * gets paging informations depending on page number, page size and total of results
     *
     * @param beginIndex   the first index of the current result
     * @param endIndex     the last index of the current result
     * @param endIndex     the size of the current result
     * @param pageSize     the size of a page
     * @param totalResults the total count of associated result
     * @param url          the bas e url used to build links
     * @param entityType   the type of the entity relative to the paging object
     * @return a {@link istic.m2.project.gofback.controllers.dto.ResponseDto.PagingDto}
     */
    public static ResponseDto.PagingDto getPagingInfo(int beginIndex, int endIndex, int nbResults, int pageSize,
                                                      int totalResults, String url, String entityType) {
        ResponseDto.PagingDto pagingDto = new ResponseDto.PagingDto();
        var rangePattern = "?begin=%s&end=%s";
        Map<String, ResponseDto.LinkDto> links = new HashMap<>();

        links.put("self", new ResponseDto.LinkDto(url + String.format(rangePattern, beginIndex, endIndex)));
        links.put("first", new ResponseDto.LinkDto(url +
                String.format(rangePattern, 0, pageSize < totalResults ? Math.min(pageSize - nbResults, nbResults - 1) :
                        totalResults - 1)));
        links.put("last", new ResponseDto.LinkDto(url +
                String.format(rangePattern, (totalResults - nbResults) + 1, totalResults - 1)));
        if (endIndex < totalResults - 1) {
            links.put("next", new ResponseDto.LinkDto(url + String.format(rangePattern, endIndex + 1,
                    Math.min(endIndex + nbResults, totalResults - 1))));
        }
        if (beginIndex > 0) {
            links.put("previous", new ResponseDto.LinkDto(url + String.format(rangePattern,
                    Math.max(beginIndex - pageSize, 0), beginIndex - 1)));
        }

        pagingDto.setCount(totalResults);
        pagingDto.setAcceptRange(entityType + " " + pageSize);
        pagingDto.setContentRange(beginIndex + "-" + endIndex + "/" + totalResults);
        pagingDto.setLinks(links);

        return pagingDto;
    }

    /**
     * gets the end index from begin and pageSize
     *
     * @param beginIndex                the beginning index
     * @param endIndex                  the ending index
     * @param paginationDefaultPageSize the max results count to return
     * @return an {@link Integer}
     */
    public static Integer getEndFromRange(Integer beginIndex, Integer endIndex, int paginationDefaultPageSize) {
        int end = endIndex;
        if (endIndex - beginIndex >= paginationDefaultPageSize) {
            end = beginIndex + paginationDefaultPageSize - 1;
        }
        return end;
    }

    /**
     * get the end index from a begin, end and pageSize
     *
     * @param begin    the beginning index
     * @param end      the end index
     * @param pageSize the max results count to return
     * @return an {@link Integer}
     */
    public static Integer getNbResults(Integer begin, Integer end, Integer pageSize) {
        return null != begin && null != end ? Math.min(pageSize, (end - begin) + 1) : pageSize;
    }

    public static boolean isPartialResponseContent(ResponseDto.PagingDto paging) {
        if (null != paging) {
            String contentRange = paging.getContentRange();
            int begin = Integer.parseInt(contentRange.substring(0, contentRange.indexOf("-")));
            int end = Integer.parseInt(contentRange.substring(contentRange.indexOf("-") + 1, contentRange.indexOf("/")));
            int totalResults = Integer.parseInt(contentRange.substring(contentRange.indexOf("/") + 1));
            if ((end - begin) + 1 < totalResults) {
                return true;
            }
        }
        return false;
    }
}
