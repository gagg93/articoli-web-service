package com.xantrix.webapp.UnitTest.RepositoryTests;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import java.util.List;

import com.xantrix.webapp.articoliwebservice.Application;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Application.class)
@SpringBootTest
public class BarcodeRepositoryTest
{
	@Autowired
	private BarcodeRepository barcodeRepository;
	
	@Test
	public void TestfindByBarcode()
	{
		assertThat(
				barcodeRepository.findByBarcode("8008490000021"))
		.extracting(Barcode::getBarcode)
		.containsOnly("8008490000021", "8008490000021");
	}
}