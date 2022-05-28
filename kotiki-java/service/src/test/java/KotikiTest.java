import com.itmo.kotiki.entity.Color;
import com.itmo.kotiki.entity.Kitty;
import com.itmo.kotiki.repository.KittyRepository;
import com.itmo.kotiki.service.implementation.KittyServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class KotikiTest {
    @Mock
    private KittyRepository kittyRepository;

    @InjectMocks
    private KittyServiceImpl kittyService;

    @Before
    public void setUp() {
        when(kittyRepository.saveAndFlush(any(Kitty.class))).thenReturn(null);
    }

    @Test
    public void findByIdKitty_KittyFound() {
        var mockedKitty = new Kitty("breed", "Name", Color.BLACK, LocalDate.now(), null);
        when(kittyRepository.findById(1L)).thenReturn(Optional.of(mockedKitty));

        var foundKitty = kittyService.findById(1L);

        verify(kittyRepository).findById(Mockito.anyLong());

        Assert.assertEquals(mockedKitty, foundKitty);
    }

    @Test
    public void addKittyFriend_FriendAdded() {
        var kitty1 = new Kitty("breed", "Name", Color.BLACK, LocalDate.now(), null);
        kitty1.setId(1L);
        var kitty2 = new Kitty("friendBreed", "Friend", Color.WHITE, LocalDate.now(), null);
        kitty2.setId(2L);

        when(kittyRepository.findById(1L)).thenReturn(Optional.of(kitty1));
        when(kittyRepository.findById(2L)).thenReturn(Optional.of(kitty2));

        kittyService.addFriend(kitty1.getId(), kitty2.getId());

        verify(kittyRepository, times(2)).saveAndFlush(Mockito.any(Kitty.class));

        Assert.assertEquals(1, kitty1.getKittyFriends().size());
        Assert.assertEquals(1, kitty2.getKittyFriends().size());
        Assert.assertEquals(kitty1.getKittyFriends().get(0), kitty2);
        Assert.assertEquals(kitty2.getKittyFriends().get(0), kitty1);
    }
}
