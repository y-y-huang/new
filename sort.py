def bubble(alist):
    l=len(alist)
    for i in range(1,l):
        flag=1
        for j in range(l-1):
            if alist[j]>alist[j+1]:
                temp=alist[j]
                alist[j]=alist[j+1]
                alist[j+1]=temp
                flag=1
        if flag==0:
            break
    return alist

def select(alist):
    l = len(alist)
    for i in range(l-1):
        mind = i
        for j in range(i+1,l-1):
            if alist[mind]>alist[j]:
                mind=j
        temp=alist[i]
        alist[i]=alist[mind]
        alist[mind]=temp
    return alist

def insert(alist):
    l = len(alist)
    for i in range(1, l):
        key = alist[i]
        j = i - 1
        while j >= 0 and key < alist[j]:
            alist[j + 1] = alist[j]
            j -= 1
        alist[j + 1] = key
    return alist

def merge_sort(alist):
    n = len(alist)
    if n <= 1:
        return alist
    # 分为两部分,对每部分进行排序
    mid = n // 2

    # 假设这已经是排序好的了
    left = merge_sort(alist[0:mid])
    right = merge_sort(alist[mid:])

    # 对排序好的进行合并
    left_index = 0
    right_index = 0
    result = []
    while left_index < len(left) and right_index < len(right):
        if left[left_index] < right[right_index]:
            result.append(left[left_index])
            left_index += 1
        else:
            result.append(right[right_index])
            right_index += 1
    # 如果左边先走到尽头，则把右边剩余所有加进来；如果右边先走到尽头，则把左边剩余所有加进来
    # 最终第一层的result把left 和 right 的result 都包含进来了
    result += left[left_index:]
    result += right[right_index:]
    return result

def quick_sort(alist, start, end):
    if start >= end:
        return alist
    mid = alist[start]
    low = start
    high = end

    while low < high:
        # 这里用alist[high] >= mid 而不是alist[high]>mid是为了把所有和中间值相等的都移动到一遍，而不是移来移去
        while low < high and alist[high] >= mid:
            high -= 1
        alist[low] = alist[high]
        while low < high and alist[low] < mid:
            low += 1
        alist[high] = alist[low]

    alist[low] = mid
    quick_sort(alist, start, low)
    quick_sort(alist, low+1, end)

def binary_chop(alist, data):
    n = len(alist)
    first = 0
    last = n - 1
    while first <= last:
        mid = (last + first) // 2
        if alist[mid] > data:
            last = mid - 1
        elif alist[mid] < data:
            first = mid + 1
        else:
            return True
    return False

if __name__=='__main__':
    li = [21, 56, 47, 5, 16, 3, 2]
    l1 = bubble(li)
    l2 =select(li)
    l3 = insert(li)
    l4 = merge_sort(li)
    l5 = quick_sort(li,0,len(li)-1)
    print("bubble sort:",l1)
    print("select sort:", l2)
    print("insert sort:", l3)
    print("merge_sort:", l4)
    print("quick_sort:", l5)