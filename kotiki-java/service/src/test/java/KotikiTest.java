import com.itmo.kotiki.dao.KittyDao;
import com.itmo.kotiki.dao.KittyFriendshipDao;
import com.itmo.kotiki.entity.Color;
import com.itmo.kotiki.entity.Kitty;
import com.itmo.kotiki.entity.KittyFriendship;
import com.itmo.kotiki.service.KittyService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class KotikiTest {
    @Mock
    private KittyDao kittyDao;

    @Mock
    private KittyFriendshipDao kittyFriendshipDao;

    @InjectMocks
    private KittyService kittyService;

    @Before
    public void setUp() {
        when(kittyDao.openCurrentSession()).thenReturn(null);
        doNothing().when(kittyDao).closeCurrentSession();

        when(kittyFriendshipDao.openCurrentSessionWithTransaction()).thenReturn(null);
        doNothing().when(kittyFriendshipDao).closeCurrentSessionWithTransaction();
        doNothing().when(kittyFriendshipDao).persist(any(KittyFriendship.class));
    }

    @Test
    public void findByIdKitty_KittyFound() {
        var mockedKitty = new Kitty("breed", "Name", Color.BLACK, LocalDate.now(), null);
        when(kittyDao.findById(1L)).thenReturn(mockedKitty);

        var foundKitty = kittyService.findById(1L);

        verify(kittyDao).openCurrentSession();
        verify(kittyDao).closeCurrentSession();
        verify(kittyDao).findById(Mockito.anyLong());

        Assert.assertEquals(mockedKitty, foundKitty);
    }

    @Test
    public void addKittyFriend_FriendAdded() {
        var kitty1 = new Kitty("breed", "Name", Color.BLACK, LocalDate.now(), null);
        var kitty2 = new Kitty("friendBreed", "Friend", Color.WHITE, LocalDate.now(), null);

        kittyService.addFriendship(kitty1, kitty2);

        verify(kittyFriendshipDao, times(2)).persist(Mockito.any(KittyFriendship.class));

        Assert.assertEquals(1, kitty1.getKittyFriendships().size());
        Assert.assertEquals(1, kitty2.getKittyFriendships().size());
        Assert.assertEquals(kitty1.getKittyFriendships().get(0).getKittysFriend(), kitty2);
        Assert.assertEquals(kitty2.getKittyFriendships().get(0).getKittysFriend(), kitty1);
    }
}
