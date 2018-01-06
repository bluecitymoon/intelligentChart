(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('PersonFansHobbyDialogController', PersonFansHobbyDialogController);

    PersonFansHobbyDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'PersonFansHobby', 'Person', 'Hobby'];

    function PersonFansHobbyDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, PersonFansHobby, Person, Hobby) {
        var vm = this;

        vm.personFansHobby = entity;
        vm.clear = clear;
        vm.save = save;
                vm.people = [];
        vm.searchPersonWithKeyword = searchPersonWithKeyword;
        function searchPersonWithKeyword(keyword) {

            if (keyword) {

                Person.loadAllByPersonName({
                    page: 0,
                    size: 10,
                    name: keyword
                }, onSuccess, onError);
            }

            function onSuccess(data) {
                vm.people = data;
            }
            function onError(error) {
                AlertService.error(error.data.message);
            }
        }
        
        vm.hobbies = Hobby.query({size: DEFAULT_HELPER_DATA_SOURCE_SIZE});

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.personFansHobby.id !== null) {
                PersonFansHobby.update(vm.personFansHobby, onSaveSuccess, onSaveError);
            } else {
                PersonFansHobby.save(vm.personFansHobby, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('intelligentChartApp:personFansHobbyUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
