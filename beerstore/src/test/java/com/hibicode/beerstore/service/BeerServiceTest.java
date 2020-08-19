package com.hibicode.beerstore.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.hibicode.beerstore.model.Beer;
import com.hibicode.beerstore.model.BeerType;
import com.hibicode.beerstore.repository.Beers;

public class BeerServiceTest {

	private BeerService beerService;

	@Mock
	private Beers beersMocked;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		beerService = new BeerService(beersMocked);
	}

	@Test(expected = BeerAlreadyExistException.class)
	public void should_deny_creation_of_beer_that_exists() {
		Beer beerInDatabase = new Beer();
		beerInDatabase.setId(10L);
		beerInDatabase.setName("Heineken");
		beerInDatabase.setVolume(new BigDecimal("355"));
		beerInDatabase.setType(BeerType.LAGER);

		when(beersMocked.findByNameAndType("Heineken", BeerType.LAGER)).thenReturn(Optional.of(beerInDatabase));

		Beer newBeer = new Beer();
		newBeer.setName("Heineken");
		newBeer.setType(BeerType.LAGER);
		newBeer.setVolume(new BigDecimal("355"));

		beerService.save(newBeer);
	}

	@Test
	public void should_create_new_beer() {
		Beer newBeer = new Beer();
		newBeer.setName("Heineken");
		newBeer.setType(BeerType.LAGER);
		newBeer.setVolume(new BigDecimal("355"));

		Beer newBeerInDatabase = new Beer();
		newBeerInDatabase.setId(10L);
		newBeerInDatabase.setName("Heineken");
		newBeerInDatabase.setType(BeerType.LAGER);
		when(beersMocked.save(newBeer)).thenReturn(newBeerInDatabase);
		Beer beerSaved = beerService.save(newBeer);

		assertThat(beerSaved.getId(), equalTo(10L));
		assertThat(beerSaved.getName(), equalTo("Heineken"));
		assertThat(beerSaved.getType(), equalTo(BeerType.LAGER));
	}

	@Test(expected = EntityNotFoundException.class)
	public void delete_when_beer_not_exist() {
		final Beer beerToUpdate = new Beer();
		beerToUpdate.setId(5L);
		beerToUpdate.setName("Heineken");
		beerToUpdate.setType(BeerType.LAGER);
		beerToUpdate.setVolume(new BigDecimal("355"));
		when(beersMocked.findById(5L)).thenReturn(Optional.empty());

		beerService.delete(5L);
	}

	@Test
	public void delete_of_an_existing_beer_that_already_exist() {
		final Beer beerInDatabase = new Beer();
		beerInDatabase.setId(10L);
		beerInDatabase.setName("Heineken");
		beerInDatabase.setType(BeerType.LAGER);
		beerInDatabase.setVolume(new BigDecimal("355"));
		when(beersMocked.findById(10L)).thenReturn(Optional.of(beerInDatabase));

		beerService.delete(10L);
	}

}