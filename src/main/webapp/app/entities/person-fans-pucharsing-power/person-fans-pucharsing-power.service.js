(function() {
    'use strict';
    angular
        .module('intelligentChartApp')
        .factory('PersonFansPucharsingPower', PersonFansPucharsingPower);

    PersonFansPucharsingPower.$inject = ['$resource'];

    function PersonFansPucharsingPower ($resource) {
        var resourceUrl =  'api/person-fans-pucharsing-powers/:id';
        var findByPersonUrl =  'api/person-fans-pucharsing-powers/person/:id';
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
