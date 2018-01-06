(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('PersonInnovationDialogController', PersonInnovationDialogController);

    PersonInnovationDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'PersonInnovation', 'Person', 'InnovationType'];

    function PersonInnovationDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, PersonInnovation, Person, InnovationType) {
        var vm = this;

        vm.personInnovation = entity;
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
        
        vm.innovationtypes = InnovationType.query({size: DEFAULT_HELPER_DATA_SOURCE_SIZE});

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.personInnovation.id !== null) {
                PersonInnovation.update(vm.personInnovation, onSaveSuccess, onSaveError);
            } else {
                PersonInnovation.save(vm.personInnovation, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('intelligentChartApp:personInnovationUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
