(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('PersonPrizeDialogController', PersonPrizeDialogController);

    PersonPrizeDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'PersonPrize', 'PrizeType', 'PrizeGroup', 'PrizeLevel', 'Person'];

    function PersonPrizeDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, PersonPrize, PrizeType, PrizeGroup, PrizeLevel, Person) {
        var vm = this;

        vm.personPrize = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.prizetypes = PrizeType.query();
        vm.prizegroups = PrizeGroup.query();
        vm.prizelevels = PrizeLevel.query();
        vm.people = Person.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.personPrize.id !== null) {
                PersonPrize.update(vm.personPrize, onSaveSuccess, onSaveError);
            } else {
                PersonPrize.save(vm.personPrize, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('intelligentChartApp:personPrizeUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.prizeDate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
