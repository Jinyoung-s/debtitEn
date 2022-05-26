import { mount } from '@vue/test-utils'
import RegisterPage from '@/views/reg/RegisterPage'

describe('RegisterPage.vue', () => {
  let wrapper
  let fieldUsername
  let fieldEmailAddress
  let fieldPassword
  let buttonSubmit

  beforeEach(()=> {
    wrapper = mount(RegisterPage)
    fieldUsername = wrapper.find('#username')

  })


  it('should regi form' , () => {
    expect(fieldUsername.element.value).toEqual('')
  })
})
