package controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static java.util.List.of;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/myservice")
@Tag(name = "MyService")
@Slf4j
public class MyController {

    private final MyService myService;

    @GetMapping("/exists")
    @Transactional(readOnly = true)
    public ResponseEntity<Void> isMy(@RequestParam("parameterName") final String someStringParameter) {
        return myService.isMy(someStringParameter)
            ? ResponseEntity.status(OK).build()
            : ResponseEntity.status(NOT_FOUND).build();
    }

    @PutMapping
    @Transactional
    public void editMy(@RequestBody @Valid final MyResource myResource) throws NoSuchMy {
        myService.editMy(myResource);
    }

    @GetMapping
    @Transactional(readOnly = true)
    public ResponseEntity<MyResource> getMy(@RequestParam(value = "parameterName") final String someStringParameter,
                                            @RequestParam(value = "myType", required = false) final MyType myType,
                                            @RequestParam(value = "myState", required = false) final MyState myState) throws NoSuchMy {
        return myService.getMy(someStringParameter, myType, myState).map(
                value -> ResponseEntity.ok(value.toMyResource()))
            .orElseThrow(() -> new NoSuchMy("My " + someStringParameter + " not found."));
    }

    @GetMapping("/{somePathVariable}")
    @Transactional(readOnly = true)
    public Page<MyResource> getTMMys(@RequestParam(value = "parameterName") final String someStringParameter,
                                     @PathVariable("somePathVariable") final String bla,
                                     @ParameterObject final Pageable pageable) {
        return myService.getTMMys(bla, someStringParameter, pageable).map(My::toMyResource);
    }
}
