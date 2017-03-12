(function() {
    'use strict';
    angular
        .module('intelligentChartApp')
        .factory('PersonFanSupportGroup', PersonFanSupportGroup);

    PersonFanSupportGroup.$inject = ['$resource'];

    function PersonFanSupportGroup ($resource) {
        var resourceUrl =  'api/person-fan-support-groups/:id';
        var findByPersonUrl =  'api/person-fan-support-groups/person/:id';
        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'loadAllByPersonId' : {
                method: 'GET',
                isArray: true,
                url: findByPersonUrl,
                params: { id : '@id'}
            },
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
