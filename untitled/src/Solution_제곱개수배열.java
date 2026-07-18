class Solution_제곱개수배열 {
    public long[] solution(int[] arr, long l, long r) {
        long[] sum = getSum(arr);
        int lIndex = indexBinarySearch(l, sum);
        int rIndex = indexBinarySearch(r, sum);
        long k = getArraySum(l, r, lIndex, rIndex, sum);
        long c = countSameSum(k, r - l, sum);
        long[] answer = {k, c};
        return answer;
    }

    long[] getSum(int[] arr) {
        int n = arr.length;
        long[] sum = new long[n + 1];
        for (int i = 0; i < n; i++) {
            sum[i + 1] = sum[i] + arr[i];
        }
        return sum;
    }

    int indexBinarySearch(long p, long[] sum) {
        int left = 0;
        int right = sum.length - 1;
        int result = 0;
        while (left <= right) {
            int mid = (left + right) >> 1;
            if (sum[mid] < p) {
                left = mid + 1;
            } else {
                result = mid;
                right = mid - 1;
            }
        }
        return result;
    }

    long getArraySum(long l, long r, int lIndex, int rIndex, long[] sum) {
        if (lIndex == rIndex) {
            return (sum[lIndex] - sum[lIndex - 1]) * (r - l + 1);
        }
        int n = sum.length;
        long result = 0;
        for (int i = lIndex + 1; i < rIndex; i++) {
            long num = (sum[i] - sum[i - 1]);
            result += num * num;
        }
        result += (sum[lIndex] - sum[lIndex - 1]) * (sum[lIndex] - l + 1);
        result += (sum[rIndex] - sum[rIndex - 1]) * (r - sum[rIndex - 1]);
        return result;
    }

    long countSameSum(long k, long range, long[] sum) {
        int n = sum.length;
        long index = 1;
        long count = 0;
        while (index + range <= sum[n - 1]) {
            long l = index;
            long r = index + range;
            int lIndex = indexBinarySearch(index, sum);
            int rIndex = indexBinarySearch(index + range, sum);
            long c = Math.min(sum[lIndex] - l + 1, sum[rIndex] - r + 1);
            long s = getArraySum(l, r, lIndex, rIndex, sum);
            long s2 = getArraySum(l + c - 1, r + c - 1, lIndex, rIndex, sum);
            long sub = Math.abs(sum[lIndex] - sum[lIndex - 1] - (sum[rIndex] - sum[rIndex - 1]));
            long max = Math.max(s, s2);
            long min = Math.min(s, s2);
            if (sub == 0 && k == s) {
                count += c;
            } else if (sub != 0 && max >= k && k >= min &&
                    ((max - k) % sub) == 0 && ((k - min) % sub) == 0) {
                count++;
            }
            index += c;

        }
        return count;
    }
}