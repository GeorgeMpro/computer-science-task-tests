package maman14;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static maman14.Ex14.*;
import static org.junit.jupiter.api.Assertions.*;

class Ex14Test {

    // Question 1
    @Test
    void whenNoPattern_shouldReturnZero() {
        assertSubStrCPatternAppears(0, "", ' ');
        assertSubStrCPatternAppears(0, "cbc", 'c');
        assertSubStrCPatternAppears(0, "cbcc", 'A');
        assertSubStrCPatternAppears(0, "AcBacBAc", 'b');
    }

    @Test
    void shouldReturnNumberOfPatternInString() {
        assertSubStrCPatternAppears(0, "cbcaBas", 'c');
        assertSubStrCPatternAppears(1, "ccc", 'c');
        assertSubStrCPatternAppears(2, "cccc", 'c');
        assertSubStrCPatternAppears(3, "ccccc", 'c');
        assertSubStrCPatternAppears(1, "cCcaBbac", 'c');
        assertSubStrCPatternAppears(1, "cbcbb", 'b');
        assertSubStrCPatternAppears(3, "ACcCCabBcDaacacc", 'c');
        assertSubStrCPatternAppears(1, "ccccCcCBAC", 'C');
        assertSubStrCPatternAppears(4, "abcbcabcacabcc", 'c');
    }

    @Test
    void whenNoPattern_thenReturnZero() {
        assertSubStrMaxPatternAppears(0, "", 'c', 0);
        assertSubStrMaxPatternAppears(0, "abc", 'c', 1);
        assertSubStrMaxPatternAppears(0, "abcAcBc", 'a', 2);
        assertSubStrMaxPatternAppears(0, "abc", 'c', 2);
    }

    @Test
    void whenGivenZeroK_shouldFindPattern() {
        assertSubStrMaxPatternAppears(1, "cc", 'c', 0);
        assertSubStrMaxPatternAppears(1, "cabcC", 'c', 0);
        assertSubStrMaxPatternAppears(2, "ccc", 'c', 0);
        assertSubStrMaxPatternAppears(2, "acbcbbaaAc", 'c', 0);
        assertSubStrMaxPatternAppears(3, "cccc", 'c', 0);
        assertSubStrMaxPatternAppears(3, "acccca", 'c', 0);
    }

    @Test
    void whenGivenKNumberOfAppearancesInBetween_thenFindPattern() {
        assertSubStrMaxPatternAppears(0, "abc", 'c', 2);
        assertSubStrMaxPatternAppears(3, "ccc", 'c', 1);
        assertSubStrMaxPatternAppears(5, "cccc", 'c', 1);
        assertSubStrMaxPatternAppears(3, "ccc", 'c', 1);
        assertSubStrMaxPatternAppears(6, "cccc", 'c', 2);
        assertSubStrMaxPatternAppears(1, "abcbc", 'c', 0);
        assertSubStrMaxPatternAppears(6, "abcbcabcacab", 'c', 2);
        assertSubStrMaxPatternAppears(6, "abcbcabcacab", 'c', 3);

    }

    // Question 2
    @Test
    void shouldModifyBetweenTwoZeros() {
        assertArrModified(arrBuilder(0, 1, 0, 1), arrBuilder(0, 1, 0, 1));
        assertArrModified(arrBuilder(0, 1, 1, 0), arrBuilder(0, 1, 1, 0));
        assertArrModified(arrBuilder(0, 1, 2, 1, 0), arrBuilder(0, 1, 1, 1, 0));
        assertArrModified(arrBuilder(0, 1, 2, 3, 2, 1, 0), arrBuilder(0, 1, 1, 1, 1, 1, 0));
        assertArrModified(arrBuilder(0, 1, 2, 3, 3, 2, 1, 0), arrBuilder(0, 1, 1, 1, 1, 1, 1, 0));
    }

    @Test
    void whenDoesNotStartWithZero_shouldModifyTheFirstDigitsLeft() {
        assertArrModified(arrBuilder(2, 1, 0, 1), arrBuilder(1, 1, 0, 1));
        assertArrModified(arrBuilder(3, 2, 1, 0), arrBuilder(1, 1, 1, 0));
        assertArrModified(arrBuilder(4, 3, 2, 1, 0, 1, 1, 0), arrBuilder(1, 1, 1, 1, 0, 1, 1, 0));
    }


    @Test
    void whenDoesNotEndWithZero_ModifyToTheRight() {
        assertArrModified(arrBuilder(1, 0, 1, 2, 3), arrBuilder(1, 0, 1, 1, 1));
        assertArrModified(arrBuilder(1, 0, 1, 2, 3, 4, 5), arrBuilder(1, 0, 1, 1, 1, 1, 1));
    }

    @Test
    void shouldModifyToTheLeftAndRight() {
        assertArrModified(arrBuilder(2, 1, 0, 1, 2), arrBuilder(1, 1, 0, 1, 1));
        assertArrModified(arrBuilder(3, 2, 1, 0, 1, 2), arrBuilder(1, 1, 1, 0, 1, 1));
        assertArrModified(arrBuilder(3, 2, 1, 0, 1, 2, 3), arrBuilder(1, 1, 1, 0, 1, 1, 1));
        assertArrModified(arrBuilder(4, 3, 2, 1, 0, 1, 2, 3, 4, 5), arrBuilder(1, 1, 1, 1, 0, 1, 1, 1, 1, 1));
    }


    @Test
    void shouldModifyFullArray() {
        assertArrModified(arrBuilder(0, 1, 2, 3, 3, 2, 1, 0, 1, 2, 3), arrBuilder(0, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1));
        assertArrModified(arrBuilder(2, 1, 0, 1, 2, 3, 3, 2, 1, 0, 1, 2, 3), arrBuilder(1, 1, 0, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1));
        assertArrModified(arrBuilder(2, 1, 0, 1, 2, 3, 3, 2, 1, 0, 1, 2, 3), arrBuilder(1, 1, 0, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1));
        assertArrModified(arrBuilder(arrBuilder(2, 1, 0, 1, 2, 3, 3, 2, 1, 0, 1, 2, 3, 4, 4, 3, 2, 1, 0, 1, 2, 3)), arrBuilder(1, 1, 0, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1));
        assertArrModified(arrBuilder(arrBuilder(2, 1, 0, 1, 2, 3, 2, 1, 0, 1, 2, 3, 4, 4, 3, 2, 1, 0, 1, 2, 3)), arrBuilder(1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1));
        //Note: expected by tester
        assertArrModified(arrBuilder(0, 1, 2, 3, 3, 2, 1, 0, 1, 2, 3, 2, 1, 0, 1, 2), arrBuilder(0, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1));
    }

    // Question 3
    @Test
    void edgeCases() {
        assertTrans("", "");
        assertNotTrans("ab", "a");
        assertNotTrans("ab", "aaa");
    }

    @Test
    void whenSingleLetter_shouldFindTransformed() {
        assertTrans("a", "a");
        assertTrans("a", "aa");
        assertNotTrans("a", "aab");
        assertNotTrans("a", "aaaaba");
        assertNotTrans("a", "acaa");
    }

    @Test
    void whenSameLettersAppear_shouldVerifyTransformed() {
        assertTrans("aa", "aa");
        assertNotTrans("aa", "ab");
        assertTrans("ab", "ab");
        assertTrans("abc", "abc");
        assertTrans("aabc", "aabc");
        assertTrans("aabbbccc", "aabbbccc");
        assertNotTrans("aabbcc", "aabbccd");
        assertNotTrans("aabbcc", "aeabbcc");
    }

    @Test
    void whenMultipleLetters_shouldFindTransformed() {
        assertTrans("aab", "aaab");
        assertTrans("aab", "aaabbb");
        assertTrans("aabc", "aaabbbc");
        assertNotTrans("aabc", "aaabbbca");
        assertNotTrans("aabc", "aaabbbcccd");

        // Note: expected by tester
        assertTrans("abbcd", "aabbccdd");
        assertNotTrans("abbcd", "abcd");
    }


    @Test
    void shouldRecognizeStandingOnExitCell() {
        assertEquals(0, countPaths(matrixBuilder(new int[]{0, 0, 0}, new int[]{0, 0, 0})));
        assertEquals(0, countPaths(matrixBuilder(arrBuilder(0, 0, 0, 0), arrBuilder(0, 0, 0, 0))));
        assertEquals(1, countPaths(new int[][]{{1}}));

    }

    @Test
    void whenSingleStep_shouldRecognizeExit() {
        assertEquals(1, countPaths(matrixBuilder(arrBuilder(11, 0), arrBuilder(0, 0))));
        assertEquals(1, countPaths(matrixBuilder(arrBuilder(12, 0, 0), arrBuilder(0, 0, 0))));
        assertEquals(1, countPaths(new int[][]{{21, 0}, {0, 0}, {0, 0}}));
        assertEquals(1, countPaths(new int[][]{{31, 0}, {0, 0}, {0, 0}, {0, 0}}));

    }

    @Test
    void whenTwoStepShould_shouldRecognizeExit() {
        assertEquals(1, countPaths(new int[][]{{11, 0}, {0, 10}, {0, 0}}));
        assertEquals(1, countPaths(new int[][]{{11, 0}, {0, 10}, {0, 10}, {0, 0}}));
        assertEquals(1, countPaths(new int[][]{{11, 0}, {10, 10}, {0, 10}, {0, 0}}));
    }

    @Test
    void expectedTestArray() {
        //Note: expected by tester
        assertEquals(3, countPaths(new int[][]{
                {12, 22, 23, 54},
                {43, 35, 21, 20},
                {34, 21, 43, 21},
                {25, 30, 0, 20},
                {0, 22, 10, 10},
                {20, 13, 3, 45}
        }));
    }


    // Helper Methods
    // Question 1

    private void assertSubStrCPatternAppears(int expectedPatternAppearances, String stringToCheck, char charToFind) {
        assertEquals(expectedPatternAppearances, subStrC(stringToCheck, charToFind));
    }

    private void assertSubStrMaxPatternAppears(int expectedAppearances, String s, char charToFind, int appearsBetweenLetters) {
        assertEquals(expectedAppearances, subStrMaxC(s, charToFind, appearsBetweenLetters));
    }

    // Question 2

    private void assertArrModified(int[] expected, int[] actual) {
        zeroDistance(actual);
        assertArrayEquals(expected, actual,
                "\nActual:   " + Arrays.toString(actual) + "\nExpected: " + Arrays.toString(expected));
    }

    private int[] arrBuilder(int... args) {
        int[] array = new int[args.length];
        System.arraycopy(args, 0, array, 0, args.length);
        return array;
    }


    // Question 3
    private void assertTrans(String original, String transformed) {
        assertTrue(isTrans(original, transformed));
    }

    private void assertNotTrans(String original, String transformed) {
        assertFalse(isTrans(original, transformed));
    }

    // Question 4
    private int[][] matrixBuilder(int[] rows, int[] cols) {
        return new int[][]{rows, cols};
    }
}
