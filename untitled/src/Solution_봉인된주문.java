import java.util.Arrays;

class Solution_봉인된주문 {
    public String solution(long n, String[] bans) {
        String answer = "";
        Arrays.sort(bans, (a, b) -> {
            if(a.length() == b.length()){
                return a.compareTo(b);
            }
            return Integer.compare(a.length(), b.length());
        });
        long original = binarySearch(n, bans);
        answer = indexToString(original);
        return answer;
    }

    long binarySearch(long n, String[] bans){
        int left = 0;
        int right = bans.length - 1;
        long result = n;
        while(left <= right){
            int mid = (left + right) >> 1;
            if(stringToOriginalIndex(bans[mid]) - mid <= n){
                left = mid + 1;
                result = n + mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return result;
    }

    long stringToOriginalIndex(String str){
        int n = str.length();
        long result = 0;
        for(int i = 0; i < n; i++) {
            result = result * 26 + str.charAt(i) - 'a'+ 1;
        }
        return result;
    }

    String indexToString(long index){
        StringBuilder sb = new StringBuilder();
        while(index > 0){
            long remain = (index - 1) % 26 + 1;
            index = (index - 1) / 26;
            char character = (char)(remain - 1 + 'a');
            sb.insert(0, character);
        }
        return sb.toString();
    }
}