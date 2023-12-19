package jeong.boardproject.service;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.IntStream;

@Service
public class PaginationService {

    private static final int BAR_LENGTH = 5;


    public List<Integer> getPaginationBarNumbers(int currentPageNumber, int totalPages) {   //정수의 리스트를 반환 List<Integer>는 숫자들을 순서대로 저장하는 리스트
        int startNumber = Math.max(currentPageNumber - (BAR_LENGTH / 2), 0);  // 0이랑 비교해서 더 큰수를 쓰겠다.
        int endNumber = Math.min(startNumber + BAR_LENGTH, totalPages);       // 총 페이지수랑 비교해서 더 작은 값을쓰겠다.



        return IntStream.range(startNumber, endNumber).boxed().toList();
    }

    public int currentBarLength() {
        return BAR_LENGTH;
    }

}