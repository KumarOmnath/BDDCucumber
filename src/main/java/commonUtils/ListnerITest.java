package commonUtils;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class ListnerITest implements ITestListener {

	@Override
	public void onTestStart(ITestResult result) {
		System.out.println("===========================================================");
		System.out.println("Test Case Execution Started,Test Name is:\t"+result.getName());
		System.out.println("===========================================================");
		
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		System.out.println("===========================================================");
		System.out.println("Test Case Executed Sucesfully,Test Name is:\t"+result.getName());
		System.out.println("===========================================================");
		
	}

	@Override
	public void onTestFailure(ITestResult result) {
		System.out.println("===========================================================");
		System.out.println("Test Case Execution Got Failed,Test Name is:\t"+result.getName());
		System.out.println("===========================================================");
		
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		System.out.println("===========================================================");
		System.out.println("Test Case is Skipped,Test Name is:\t"+result.getName());
		System.out.println("===========================================================");
		
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onFinish(ITestContext context) {
		// TODO Auto-generated method stub
		
	}

}
