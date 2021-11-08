package keboom.optional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import org.apache.commons.collections.CollectionUtils;

public class Demo1 {

  public static void main(String[] args) {

    List<Integer> list = new ArrayList<>();
    list.add(null);
//    list.add(2);

//    String collect = Optional.ofNullable(list)
//        .orElse(Collections.emptyList())
//        .stream()
//        .filter(Objects::nonNull)
//        .map(Object::toString)
//        .collect(Collectors.joining(","));
//
//    System.out.println(collect);

    //例子：
//    private List<CommendVo> convertToCommentDTO(List<CommentDTO> queryResultList) {
//      return Optional.ofNullable(queryResultList).orElse(Collections.emptyList()).stream()
//          .map(this::convertToCommendVo)
//          .filter(Objects::nonNull)
//          .collect(Collectors.toList());
//    }

    /*
     private Map<Long, AtlasVo> convertToAtlasVoList(List<BaseAtlas> baseAtlasList) {
    return Optional.ofNullable(baseAtlasList)
        .orElse(Collections.emptyList())
        .stream()
        .map(this::convertToAtlasVo)
        .filter(Objects::nonNull)
        .collect(Collectors.toMap(AtlasVo::getAtlasId, Function.identity()));
  }
     */

    /* 最终集合返回 vo，function.identity 自己调自己，感觉没啥用。。？？
    private Map<Long, AtlasVo> convertToAtlasVoList(List<BaseAtlas> baseAtlasList) {
    return Optional.ofNullable(baseAtlasList)
        .orElse(Collections.emptyList())
        .stream()
        .map(this::convertToAtlasVo)
        .filter(Objects::nonNull)
        .collect(Collectors.toMap(AtlasVo::getAtlasId, Function.identity()));
  }
     */


    /*
    bannerResultVO.setInventoryIds(Optional.ofNullable(source.getInventoryIds()).orElse(Collections.emptyList())
                                       .stream()
                                       .filter(Objects::nonNull)
                                       .map(Object::toString)
                                       .collect(Collectors.joining(",")));

     */

  }

}
