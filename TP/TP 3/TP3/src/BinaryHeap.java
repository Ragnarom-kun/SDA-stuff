//import java.util.ArrayList;
//
//public class BinaryHeap<T extends Comparable<T>> {
//	ArrayList<T> data;
//
//	public BinaryHeap() {
//		data = new ArrayList<T>();
//	}
//
//	public BinaryHeap(ArrayList<T> arr) {
//		data = arr;
//		heapify();
//	}
//
//	public T peek() {
//		if (data.isEmpty())
//			return null;
//		return data.get(0);
//	}
//
//	public void insert(T value) {
//		data.add(value);
//		percolateUp(data.size() - 1);
//	}
//
//	public T remove() {
//		T removedObject = peek();
//
//		if (data.size() == 1)
//			data.clear();
//		else {
//			data.set(0, data.get(data.size() - 1));
//			data.remove(data.size() - 1);
//			percolateDown(0);
//		}
//
//		return removedObject;
//	}
//
//	private void percolateDown(int idx) {
//		T node = data.get(idx);
//		int heapSize = data.size();
//
//		while (true) {
//			int leftIdx = getLeftChildIdx(idx);
//			if (leftIdx >= heapSize) {
//				data.set(idx, node);
//				break;
//			} else {
//				int minChildIdx = leftIdx;
//				int rightIdx = getRightChildIdx(idx);
//				if (rightIdx < heapSize && data.get(rightIdx).compareTo(data.get(leftIdx)) < 0)
//					minChildIdx = rightIdx;
//
//				if (node.compareTo(data.get(minChildIdx)) > 0) {
//					data.set(idx, data.get(minChildIdx));
//					idx = minChildIdx;
//				} else {
//					data.set(idx, node);
//					break;
//				}
//			}
//		}
//	}
//
//	private void percolateUp(int idx) {
//		T node = data.get(idx);
//		int parentIdx = getParentIdx(idx);
//		while (idx > 0 && node.compareTo(data.get(parentIdx)) < 0) {
//			data.set(idx, data.get(parentIdx));
//			idx = parentIdx;
//			parentIdx = getParentIdx(idx);
//		}
//
//		data.set(idx, node);
//	}
//
//	private int getParentIdx(int i) {
//		return (i - 1) / 2;
//	}
//
//	private int getLeftChildIdx(int i) {
//		return 2 * i + 1;
//	}
//
//	private int getRightChildIdx(int i) {
//		return 2 * i + 2;
//	}
//
//	private void heapify() {
//		for (int i = data.size() / 2 - 1; i >= 0; i--)
//			percolateDown(i);
//	}
//
//	public void sort() {
//		int n = data.size();
//		while (n > 1) {
//			data.set(n - 1, remove(n));
//			n--;
//		}
//	}
//
//	public T remove(int n) {
//		T removedObject = peek();
//
//		if (n > 1) {
//			data.set(0, data.get(n - 1));
//			percolateDown(0, n - 1);
//		}
//
//		return removedObject;
//	}
//
//	private void percolateDown(int idx, int n) {
//		T node = data.get(idx);
//		int heapSize = n;
//
//		while (true) {
//			int leftIdx = getLeftChildIdx(idx);
//			if (leftIdx >= heapSize) {
//				data.set(idx, node);
//				break;
//			} else {
//				int minChildIdx = leftIdx;
//				int rightIdx = getRightChildIdx(idx);
//				if (rightIdx < heapSize && data.get(rightIdx).compareTo(data.get(leftIdx)) < 0)
//					minChildIdx = rightIdx;
//
//				if (node.compareTo(data.get(minChildIdx)) > 0) {
//					data.set(idx, data.get(minChildIdx));
//					idx = minChildIdx;
//				} else {
//					data.set(idx, node);
//					break;
//				}
//			}
//		}
//	}
//
//}
